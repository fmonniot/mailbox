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
mvn install

export GLASSFISH_HOME=path/to/glassfish
mvn -pl directory-manager org.glassfish.maven.plugin:maven-glassfish-plugin:deploy
mvn -pl mailbox-manager org.glassfish.maven.plugin:maven-glassfish-plugin:deploy
```

there is two "client" that be used to test these services :

```bash
mvn -pl admin-client exec:java
mvn -pl mail-client exec:java
```

## Documentation

You will find some documentation under the `docs` folder (this documentation is in french)
