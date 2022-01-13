#-d <database>
#-u <username>
#-m <userdomain>
#-p <password

#while getopts d:u:m:p: flag
#do
#	case "${flag}" in 
#		d) MAINDB=${OPTARG};;
#		u) USERNAME=${OPTARG};;
#		m) USERDOMAIN=${OPTARG};;
#		p) PASSWORD=${OPTARG};;
#	esac
#done

#echo ${MAINDB}
#echo ${USERNAME}
#echo ${USERDOMAIN}
#echo ${PASSWORD}

#read -s -p " Enter db root password: " rootpasswd

MAINDB=test001 
USERNAME=user001
MYSQLHOST=172.30.80.1
#MYSQLHOST=localhost
USERDOMAIN=172.30.85.143
PASSWORD=TingLing001
rootpasswd=CocaCola*001



mysql -uroot -p${rootpasswd} -h${MYSQLHOST} --protocol=TCP -e "CREATE DATABASE ${MAINDB} /*\!40100 DEFAULT CHARACTER SET utf8 */;"
mysql -uroot -p${rootpasswd} -h${MYSQLHOST} --protocol=TCP -e "CREATE USER '${USERNAME}'@'${USERDOMAIN}' IDENTIFIED BY '${PASSWORD}';"
mysql -uroot -p${rootpasswd} -h${MYSQLHOST} --protocol=TCP -e "GRANT ALL PRIVILEGES ON ${MAINDB}.* TO '${USERNAME}'@'${USERDOMAIN}';"
mysql -uroot -p${rootpasswd} -h${MYSQLHOST} --protocol=TCP -e "FLUSH PRIVILEGES;"