CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE roles (
   id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
   name varchar(100) NOT NULL UNIQUE
);


CREATE TABLE users (
       id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
       username varchar(255) NOT NULL UNIQUE,
       email varchar(255) UNIQUE,
       password_hash text NOT NULL,
       enabled boolean DEFAULT true,
       mfa_enabled boolean DEFAULT false,
       mfa_secret text NULL,
       created_at timestamptz DEFAULT now(),
       updated_at timestamptz DEFAULT now()
);


CREATE TABLE user_roles (
    user_id uuid REFERENCES users(id) ON DELETE CASCADE,
    role_id uuid REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY(user_id, role_id)
);


-- Tabela para refresh tokens (armazenar hash do token)
CREATE TABLE refresh_tokens (
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id uuid REFERENCES users(id),
    token_hash text NOT NULL,
    client_id varchar(255),
    expires_at timestamptz,
    revoked boolean DEFAULT false,
    created_at timestamptz DEFAULT now()
);


CREATE TABLE audit_logs (
    id bigserial PRIMARY KEY,
    user_id uuid NULL,
    event varchar(100),
    details jsonb,
    created_at timestamptz DEFAULT now()
);