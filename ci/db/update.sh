#!/bin/bash

apt-get -y update
apt-get -y install mysql-client

sh resource-from-git/ci/db/createdb.sh