#-d <database>
#-u <username>
#-m <userdomain>
#-p <password

while getopts d:u:m:p: flag
do
	case "${flag}" in 
		d) MAINDB=${OPTARG};;
		u) USERNAME=${OPTARG};;
		m) USERDOMAIN=${OPTARG};;
		p) PASSWORD=${OPTARG};;
	esac
done

#echo ${MAINDB}
#echo ${USERNAME}
#echo ${USERDOMAIN}
#echo ${PASSWORD}

read -s -p " Enter db root password: " rootpasswd

mysql -uroot -p${rootpasswd} -h172.26.176.1 --protocol=TCP -e "CREATE DATABASE ${MAINDB} /*\!40100 DEFAULT CHARACTER SET utf8 */;"
mysql -uroot -p${rootpasswd} -h172.26.176.1 --protocol=TCP -e "CREATE USER '${USERNAME}'@'${USERDOMAIN}' IDENTIFIED BY '${PASSWORD}';"
mysql -uroot -p${rootpasswd} -h172.26.176.1 --protocol=TCP -e "GRANT ALL PRIVILEGES ON ${MAINDB}.* TO '${USERNAME}'@'${USERDOMAIN}';"
mysql -uroot -p${rootpasswd} -h172.26.176.1 --protocol=TCP -e "FLUSH PRIVILEGES;"