#!/bin/bash

source env.sh

# Comprueba si docker esta instalado, si no lo esta termina la ejecucion del script
LOG_FILE_NAME="log_$(date +%Y-%m-%d_%H-%M-%S)"
touch ./logs/$LOG_FILE_NAME
echo "[INFO] Comprobando si Docker esta instalado..." | tee -a ./logs/$LOG_FILE_NAME
docker -v >> ./logs/$LOG_FILE_NAME
retorno=$?
if [ $retorno -ne 0 ]; then
	echo "[ERROR] Docker no instalado en el dispositivo" | tee -a ./logs/$LOG_FILE_NAME
	exit
fi 

echo "[INFO] Comprobando si docker funciona correctamente..." | tee -a ./logs/$LOG_FILE_NAME
systemctl is-active docker.socket >> ./logs/$LOG_FILE_NAME
systemctl is-active docker >> ./logs/$LOG_FILE_NAME
dockerStatus=$? # Retorno 0 significa que el servicio está activo

if [ $dockerStatus -ne 0 ]; then
	echo "[INFO] Iniciando Docker..."
	sudo systemctl start docker.socket
	sudo systemctl start docker

fi

# Comprueba si el puerto esta en uso
sudo lsof -i :$PORT >> ./logs/$LOG_FILE_NAME
retorno=$?
if [ $retorno -eq 0 ]; then
	
	echo -e "\n[ERROR] El puerto $PORT esta en uso, aqui algunos detalles:" | tee -a ./logs/$LOG_FILE_NAME
	echo -e "\nNota:\tpuedes desactivar cualquier servicio con el siguiente comando:" | tee -a ./logs/$LOG_FILE_NAME
	echo -e "\n\t\033[32msudo systemctl stop <service-name>\n" | tee -a ./logs/$LOG_FILE_NAME
	exit
fi

#Lanza el contenedor de Docker
docker run -d -it \
	--name $CONTAINER_NAME \
	-p $PORT:$PORT \
	-e MYSQL_ROOT_PASSWORD=$ROOT_PASSWORD \
	--env MARIADB_USER=$DB_USER \
	--env MARIADB_PASSWORD=$DB_PASSWORD \
	-v ./sql/init.sql:/docker-entrypoint-initdb.d/init.sql \
	mariadb:latest

retorno=$?
if [ $retorno -ne 0 ]; then
	echo -e "[ERROR] Ocurrio un error inesperado al ejecutar \033[32m<docker run>" | tee -a ./logs/$LOG_FILE_NAME
	echo -e "\n\tUn posible error: Docker Desktop no fue iniciado." | tee -a ./logs/$LOG_FILE_NAME
	exit
fi

echo "[INFO] Se creo el container $CONTAINER_NAME" | tee -a ./logs/$LOG_FILE_NAME
