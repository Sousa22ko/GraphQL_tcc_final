create table if not exists cargo (
    id_cargo SERIAL NOT NULL PRIMARY KEY,
    ativo BOOLEAN NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    data_modificacao TIMESTAMP,
    denominacao VARCHAR(255) NOT NULL UNIQUE
)

create table if not exists pessoa (
    id_pessoa SERIAL NOT NULL PRIMARY KEY,
    ativo BOOLEAN NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    data_modificacao TIMESTAMP,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone_celular VARCHAR(50),
    telefone_residencial VARCHAR(50),
    data_nascimento DATE,
    cpf VARCHAR(20) NOT NULL UNIQUE,
    status_cadastro BOOLEAN NOT NULL,
    id_endereco INTEGER
)

create table if not exists endereco (
    id_endereco SERIAL NOT NULL PRIMARY KEY,
    ativo BOOLEAN NOT NULL,
    data_criacao TIMESTAMP   NOT NULL,
    data_modificacao TIMESTAMP  ,
    bairro VARCHAR(255) NOT NULL,
    cep VARCHAR(255) NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    complemento VARCHAR(255),
    estado VARCHAR(255) NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(255),
    pais VARCHAR(255) NOT NULL,
    ponto_referencia VARCHAR(255)
)

create table if not exists ferias (
    id_ferias SERIAL NOT NULL PRIMARY KEY,
    ativo BOOLEAN NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    data_modificacao TIMESTAMP,
    ano_referencia VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    id_pessoa INTEGER
)

create table if not exists ferias_parcela (
    id_ferias_parcela SERIAL NOT NULL PRIMARY KEY,
    ativo BOOLEAN NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    data_modificacao TIMESTAMP,
    fim DATE NOT NULL,
    inicio DATE NOT NULL,
    id_ferias INTEGER
)

create table if not exists vinculo (
    id_vinculo SERIAL NOT NULL PRIMARY KEY,
    ativo BOOLEAN NOT NULL,
    data_criacao TIMESTAMP  NOT NULL,
    data_modificacao TIMESTAMP ,
    data_admissao DATE,
    jornada_semanal INTEGER,
    tipo_vinculo VARCHAR(50),
    matricula VARCHAR(75),
    id_cargo INTEGER,
    id_pessoa INTEGER,
    id_setor INTEGER,
    status_vinculo BOOLEAN NOT NULL
)

create table if not exists setor (
    id_setor SERIAL NOT NULL PRIMARY KEY,
    ativo BOOLEAN NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    data_modificacao TIMESTAMP  ,
    codigo VARCHAR(2) NULL,
    id_setor_pai INTEGER NULL,
    denominacao VARCHAR(75) NOT NULL,
    sigla VARCHAR(3) NOT NULL,
    tipo_setor VARCHAR(50) NOT NULL
)