# proj_dev_web
Projeto da matéria desenvolvimento Web

## Como rodar

Isso funciona no linux, em windows a melhor abordagem é usar o eclipse.

Voce vai precisar de um banco de dados MySQL tambem.

### 1) Descompactar o Tomcat

Descompacte o .zip do Tomcat em [apps/](apps):

```bash
cd apps
unzip apache-tomcat-10.1.54.zip
```

### 2) Configurar o TOMCAT_HOME

Edite o arquivo [.env](.env) e aponte para a pasta descompactada no seu computador:

```bash
TOMCAT_HOME=/home/luiz/Projects/UFF/proj_dev_web/apps/apache-tomcat-10.1.54
```

### 3) Configurar o banco

O script usa o arquivo [setup.sql](setup.sql) para criar banco, usuario e tabelas.
Voce pode ajustar as credenciais em [.env](.env):

```bash
DB_URL=jdbc:mysql://127.0.0.1:3306/dbteste?useTimezone=true&serverTimezone=UTC
DB_USER=app
DB_PASS=app123
```

Usuario default do sistema:

- email: admin@local
- senha: 123

### 4) Compilar, empacotar e subir

Use o script [run.sh](run.sh). Ele executa o setup do MySQL usando `sudo`:

```bash
./run.sh
```

O Tomcat vai iniciar em foreground. Para acessar:

```
http://localhost:8080/proj_dev_web/
```
