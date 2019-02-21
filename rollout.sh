#!/usr/bin/env bash

#Prebuild
sbt clean application/stage

#Build
version=`sbt -Dsbt.log.noformat=true "show application/version" | tail -n 1 | awk '{print $2}'`
image=abimbola/xplore:$version
docker build -t $image .

#Auth
docker login

#Push
docker push $image

#Deploy
ssh -i xplore.pem ubuntu@godis.org.uk "docker pull $image"
ssh -i xplore.pem ubuntu@godis.org.uk "docker service update --image $image xplore"
