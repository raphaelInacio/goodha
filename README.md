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
  
``gcloud app deploy  src/main/appengine/accounts-service.yaml``
``https://accounts-service-dot-macro-scion-300810.rj.r.appspot.com/v1/accounts/6025144992530432``

- **Habits** Responsável por gerenciar as todos os habitos criados no sistema, hoje o sistema já possui alguns habitos
  padrão para todos os usuário como também permite a criação de habitos exclusivos

``https://habits-service-dot-macro-scion-300810.rj.r.appspot.com/v1/habits``
``gcloud app deploy  src/main/appengine/habits-service.yaml``

- **Records** Resposabilidade do microserviço de records é registrar as atividades dos usuários,


#### Problemas

- Configuração de services do app engine
- Congigurar um proxy entre o datastore 



#### Comandos uteis

* Subir o emulador do Datastore ``gcloud beta emulators datastore start``

* Deploy app no appengine ``gcloud app deploy``

* Deploy app no appengine via mvn ``mvn clean package appengine:deploy``

* Deploy da Documentação da API ``gcloud endpoints services deploy swagger.yaml --validate-only``

* URl do projeto ``https://macro-scion-300810.rj.r.appspot.com/v1/habits/hello``

* URl do portal do desenvolvedor ``https://endpointsportal.macro-scion-300810.cloud.goog/``
