# Mail Box Manager

A simple mail box system based on JEE.

## Git repository

The most up-to-date version of this project is available on https://github.com/fmonniot/mailbox.

## Use

First, get the code :

```bash
git clone https://github.com/fmonniot/mailbox
```

or, if your are using an archived version

```bash
tar -xvzf mailbox.tar.gz
```

then build everything and deploy the Glassfish applications :

```bash
cd mailbox
mvn package
mvn -pl directory-manager org.glassfish.maven.plugin:maven-glassfish-plugin:deploy
mvn -pl mailbox-manager org.glassfish.maven.plugin:maven-glassfish-plugin:deploy
```