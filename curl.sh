#!/bin/bash

#CODE_VERIFIER='K-Ifn1yDvlb3wxekb7V9zccvKNptfSOXydK8V7utck5-dfIulltp-hbPf_Y17caI4OiF7gNeqD8UrxfoUI-40gEaGUSuCcZvov5a972fSoQFth7GOesQ2HmmhKTXHmUb'

# Criar o code_challenge
#CODE_CHALLENGE=$(echo -n $CODE_VERIFIER | openssl dgst -sha256 -binary | openssl base64 | tr -d '=+/')

#echo $CODE_CHALLENGE
#CONTENT=$(curl -X POST 'http://localhost:8085/auth/login' \
#     -H "Content-type:application/json" \
#     -d '{"username":"jean.oliveira","password":"123"}' | jq .token | tr -d '"')

  #CONTENT=$(curl http://localhost:8085/oauth2/authorize?response_type=code&client_id=demo-client&redirect_uri=http://localhost:8085/login/oauth2/code/demo-client&scope=openid)
#COOKIES=$(curl -X POST "http://localhost:8085/login" \
#  -H "Content-Type: application/x-www-form-urlencoded" \
#  -d "username=jean.oliveira" \
#  -d "password=123" \
#  -c cookies.txt -i)
#
#echo $COOKIES

CONTENT=$(curl -s -X POST "http://localhost:8085/oauth2/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=demo-client" \
  -d "client_secret=123" \
  -d "grant_type=client_credentials" \
  -d "scope=openid" | jq -r '.access_token')
#

#CONTENT=$(curl -s -X POST "http://localhost:8085/oauth2/token" \
#  -H "Content-Type: application/x-www-form-urlencoded" \
#  -d "client_id=demo-client" \
#  -d "username=user" \
#  -d "password=password" \
#  -d "grant_type=password" \
#  -d "scope=openid" | jq -r '.access_token')


#echo $CONTENT

curl "$@" \
  -H "Authorization: Bearer $CONTENT" | jq


