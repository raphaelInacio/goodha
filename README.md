# Goodha App

## Estudo da plataforma GCP

Este projeto tem como objetivo criar uma solução de microserviços utilizando os recursos da Google Cloud

Esse estudo esta divido em duas partes

1 - Criação de microservicos utilizando app engine
2 - Criação de miroservicos utilizando a plataforme Google Kubernetes Engine

## Estudo de caso

Temos um aplicativo Goodha https://play.google.com/store/apps/details?id=com.raphaelinacio.goodha que foi construido em
flutter + firebase onde todas as regras de negócio ficam no aplicativo.

Precisamos expandir o aplicativo e criar uma solução WEB, porém como as regras de negócios estão no aplicativo, não é
possível reaproveita-las em outros canais.

A solução proposta foi criar um backend para prover as regras de negócio e com isso todos os canais podem reaproveitar a
inteligencia de negócio através de **APIS**.

A arquitetura precisa ser flexível ao ponto que nos possibilite escalar o aplicativo caso necessário, ter um baixo custo
de infra e operação pois não temos um time devops em nossa equipe então precisamos de uma solução NO OPS de preferência
ou asd o minino de operação.

### Stack Proposta

* Springboot
* Java 11
* Google App Engine
* Cloud Datastore
* Cloud Endpoints
* Cloud Pub Sub

### Passo 1 - Criando uma conta no Google Cloud

Crie uma conta no google cloud utilizando

## Passo 2

Construir componentes do spring boot com ``https://start.spring.io/``

## Passo 3

Construir componentes do spring boot com ``https://start.spring.io/``

### Estrtutura do projeto

O projeto está estruturado como um monorepo divido em 3 microserviços

- **Accounts** Responsável por gerenciar as accounts do sistema, hoje o sisitema possui apenas duas accounts, `USER`
  e `ADMIN`
  
* ``gcloud app deploy  src/main/appengine/accounts-service.yaml``
* ``https://accounts-service-dot-macro-scion-300810.rj.r.appspot.com/v1/accounts/6025144992530432``
* `` docker build -t raphaelinacio/accounts-service .``
* `` docker run --publish 8080:8080 -v ~/.config:/root/.config raphaelinacio/accounts-service``

- **Habits** Responsável por gerenciar as todos os habitos criados no sistema, hoje o sistema já possui alguns habitos
  padrão para todos os usuário como também permite a criação de habitos exclusivos

* ``https://habits-service-dot-macro-scion-300810.rj.r.appspot.com/v1/habits``
* ``gcloud app deploy  src/main/appengine/habits-service.yaml``
* ``ddocker build -t raphaelinacio/habits-service .``
* `` docker run --publish 8080:8080 -v ~/.config:/root/.config raphaelinacio/habits-service``


- **Records** Resposabilidade do microserviço de records é registrar as atividades dos usuários,
  
* ``https://habits-service-dot-macro-scion-300810.rj.r.appspot.com/v1/habits``
*  ``gcloud app deploy  src/main/appengine/habits-service.yaml``
*  ``docker build -t raphaelinacio/records-service .``
* `` docker run --publish 8080:8080 -v ~/.config:/root/.config raphaelinacio/records-service``

#### Problemas

- Configuração de services do app engine
- Congigurar um proxy entre o datastore 
- Criar um loadbalancer com kind

https://kind.sigs.k8s.io/docs/user/loadbalancer/


#### Comandos uteis

* Subir o emulador do Datastore ``gcloud beta emulators datastore start``

* Deploy app no appengine ``gcloud app deploy``

* Deploy app no appengine via mvn ``mvn clean package appengine:deploy``

* Deploy da Documentação da API ``gcloud endpoints services deploy swagger.yaml --validate-only``

* URl do projeto ``https://macro-scion-300810.rj.r.appspot.com/v1/habits/hello``

* URl do portal do desenvolvedor ``https://endpointsportal.macro-scion-300810.cloud.goog/``

* docker build --build-arg JAR_FILE=build/libs/\*.jar -t raphaelinacio/accounts-service .


#### Metricas

* JVM
* HEAP
* PERM
* SUCESSS/ERROR
* RESPONSE TIME
* CLIENT RESPONSE TIME
* TOMCAT TREADS