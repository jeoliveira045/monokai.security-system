# Security System

Projeto de autenticação e autorização baseado em Spring Boot com Spring Authorization Server (OAuth2/OIDC), JWT, PostgreSQL e migrações Flyway. Inclui endpoints simples de administração de usuários para demonstrar proteção de recursos.

## Sumário
- Visão geral
- Arquitetura e tecnologias
- Pré-requisitos
- Como executar (dev)
- Configurações
- Migrações de banco (Flyway)
- Endpoints principais
- Autenticação e Autorização (OAuth2/OIDC + Login de formulário)
- Exemplos (cURL)
- Troubleshooting

## Visão geral
Este projeto expõe:
- Um Authorization Server OAuth2/OIDC que emite tokens JWT (assinados com chave RSA em memória) usando Spring Authorization Server.
- Um conjunto de endpoints REST protegidos sob `/api/users` para listar e criar usuários.
- Persistência em PostgreSQL com versionamento de esquema via Flyway.

Porta padrão da aplicação: `8085` (ver `src/main/resources/application.properties`).

## Arquitetura e tecnologias
- Java + Spring Boot
- Spring Security + Spring Authorization Server (OIDC)
- JWT (Nimbus JOSE + JWT)
- PostgreSQL 16 (via Docker Compose)
- Flyway para migrações do banco
- Maven (build/execução)

## Pré-requisitos
- Java 17+ (recomendado)
- Maven 3.8+
- Docker + Docker Compose (para o PostgreSQL)

## Como executar (dev)
1) Suba o PostgreSQL com Docker Compose:
   - No diretório do projeto, execute:
     - `docker compose up -d`
   - Isso criará um banco `authdb` com usuário `auth` e senha `secret` em `localhost:5432`.

2) Ajuste as configurações se necessário em `src/main/resources/application.properties`.

3) Rode a aplicação Spring Boot:
   - `./mvnw spring-boot:run`
   - A aplicação iniciará em `http://localhost:8085`.

4) Acesse a página de login (para sessão de usuário):
   - `http://localhost:8085/login`
   - Usuário padrão (em memória):
     - username: `user`
     - password: `password`

Observação: as credenciais acima são apenas para desenvolvimento/demonstração.

## Configurações
Arquivo: `src/main/resources/application.properties`
- `server.port=8085`
- `spring.datasource.url=jdbc:postgresql://localhost:5432/authdb`
- `spring.datasource.username=auth`
- `spring.datasource.password=secret`
- `spring.jpa.hibernate.ddl-auto=validate`
- Flyway habilitado: `spring.flyway.enabled=true`

Você pode sobrescrever essas propriedades via variáveis de ambiente ou parâmetros de linha de comando do Spring.

## Migrações de banco (Flyway)
As migrações estão em `src/main/resources/db.migration` e são executadas automaticamente na inicialização.
- `V20250911232435__CREATE_USER_ROLES.sql` — cria tabelas de usuários e papéis.
- `V20250926151440__CREATE_OAUTH2_CLIENT.sql` — configurações relacionadas a clientes OAuth2 (se aplicável).

## Endpoints principais
- `GET /api/users` — lista usuários (requer autenticação)
- `POST /api/users` — cria um novo usuário (requer autenticação)
  - Body JSON esperado:
    - `{ "username": "john", "password": "s3cr3t", "email": "john@example.com" }`

Obs.: A proteção global exige autenticação para qualquer requisição. Você pode autenticar via sessão (login de formulário) ou via token (quando aplicável).

## Autenticação e Autorização
Há duas formas principais para fins de desenvolvimento:

1) Login de formulário (sessão)
- Acesse `http://localhost:8085/login` e autentique com `user/password`.
- Após autenticar, você pode chamar os endpoints protegidos na mesma sessão do navegador.

2) Authorization Server OAuth2/OIDC (tokens JWT)
- O projeto inclui uma configuração básica do Authorization Server (Spring Authorization Server) com um cliente em memória:
  - client_id: `oidc-client`
  - client_secret: `secret` (método `client_secret_basic`)
  - `authorization_code` + `refresh_token`
  - Scopes: `openid`, `profile`
  - Redirect URI de exemplo: `http://127.0.0.1:8080/login/oauth2/code/oidc-client`

Importante:
- A aplicação deste repositório roda em `8085`, enquanto a `redirectUri` configurada aponta para `127.0.0.1:8080` (um client app externo). Para testar o fluxo Authorization Code, você precisa de um cliente OIDC rodando nessa URL/porta, ou ajustar a `redirectUri` do cliente conforme seu cenário.
- Endpoints padrão do Authorization Server (exemplos):
  - Descoberta OIDC: `http://localhost:8085/.well-known/openid-configuration`
  - Autorizar: `http://localhost:8085/oauth2/authorize`
  - Token: `http://localhost:8085/oauth2/token`

Os JWTs são assinados com uma chave RSA gerada em memória a cada inicialização (apenas para dev). Em produção, configure um keystore persistente.

## Exemplos (cURL)
A) Testar a sessão via login de formulário (navegador):
- Abra `http://localhost:8085/login`, autentique com `user/password` e depois teste `GET /api/users` no navegador ou via ferramenta que reutilize cookies/sessão.

B) Exemplo de chamada autenticada por sessão usando cookie (após login, exporte o cookie JSESSIONID):
- `curl -v --cookie "JSESSIONID=seu_cookie_aqui" http://localhost:8085/api/users`

C) Fluxo OAuth2 (Authorization Code) — Requer um cliente OIDC configurado para a redirectUri indicada. Se você tiver um app cliente em `127.0.0.1:8080`, acesse no navegador:
- `http://localhost:8085/oauth2/authorize?response_type=code&client_id=oidc-client&scope=openid%20profile&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client`
- Autentique como `user/password`, autorize o acesso e o client receberá o `code`. O client então troca o `code` por `access_token` em `POST /oauth2/token` usando `client_secret_basic`.

D) Com um access_token válido (JWT), chame os recursos:
- `curl -H "Authorization: Bearer <ACCESS_TOKEN>" http://localhost:8085/api/users`

## Troubleshooting
- Erro de conexão com banco: garanta que o container do Postgres está rodando (`docker compose ps`) e que as credenciais batem com `application.properties`.
- 401/403 ao acessar `/api/users`: você precisa estar autenticado (login de formulário) ou enviar um token válido via `Authorization: Bearer ...`.
- Erro no fluxo Authorization Code por causa da `redirectUri`: ajuste o client em memória no código (`AuthServerConfig.registeredClientRepository`) para corresponder ao seu ambiente de testes.
- Esquema do banco divergente: confira logs do Flyway e verifique se `spring.jpa.hibernate.ddl-auto=validate` não conflita; rode as migrações.

## Scripts úteis
- `docker-compose.yml` para banco PostgreSQL
- `curl.sh` pode conter exemplos auxiliares (edite para seu cenário)

## Licença
Projeto para fins educacionais/demonstração. Ajuste e utilize conforme suas necessidades.
