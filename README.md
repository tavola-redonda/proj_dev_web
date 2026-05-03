# proj_dev_web
Projeto da matéria desenvolvimento Web

## Como rodar

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

### 3) Compilar, empacotar e subir

Use o script [run.sh](run.sh):

```bash
./run.sh
```

O Tomcat vai iniciar em foreground. Para acessar:

```
http://localhost:8080/proj_dev_web/
```
