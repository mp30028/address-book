#!/bin/bash
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

echo ${MAINDB}
echo ${USERNAME}
echo ${USERDOMAIN}
echo ${PASSWORD}