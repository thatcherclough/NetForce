# NetForce
NetForce: A network scanning and SSH brute forcing tool.

## Features
NetForce can both scan a network for open ports and brute force attack an SSH server.

It can scan a network when given a range of IP addresses along with a port to scan for.
This will return all IP addresses in the given range with the given port open.

NetForce can also brute force attack an SSH server in attempt to obtain a username and/or password.
This requires a host address/name, port, user or file contains usernames to try, and a password or file containing passwords to try.
Common SSH usernames and passwords can be found in src/main/resources/.

## Demo
<a href="https://asciinema.org/a/272184" target="_blank"><img src="https://asciinema.org/a/272184.svg" width="600"/></a>

## Requirements
- A Java JDK distribution >=8 must be installed and added to PATH.

## Compatibility
NetForce is compatible with Windows, Mac, and Linux.

## Installation
```
# clone NetForce
git clone https://github.com/thatcherclough/NetForce.git

# change the working directory to NetForce
cd NetForce

# build NetForce with Maven
# for Windows run
mvnw.cmd clean package

# for Linux run
chmod +x mvnw
./mvnw clean package

# for Mac run
sh mvnw clean package
```

Alternatively, you can download the jar from the [release page](https://github.com/thatcherclough/NetForce/releases).

## Usage
```
java -jar netforce.jar
NetForce: A network scanning and SSH brute forcing tool (1.0.0)

Usage:
	java -jar netforce.jar [-h] [-v] [scan -t IPRANGE -p PORT -w TIMEOUT]
			       [brute -t HOST -p PORT -un USER/FILE -pw PASSWORD/FILE -w TIMEOUT]
Arguments:
	-h,  --help		Display this message.
	-v,  --version		Display current version.
	-t,  --target		Specify IP range when scanning or host when brute forcing.
	-p,  --port		Specify port to use. (Set to 22 by default)
	-un, --user		Specify username or file containing usernames to use.
	-pw, --pass		Specify password or file containing passwords to use.
	-w,  --wait		Specify connection timeout in milliseconds. (Set to 1000 by default)

```
### Examples of usage:
```
java -jar netforce.jar scan -t 192.168.86.1-255 -w 100
java -jar netforce.jar scan -t 192.168.1-5.1-255 -p 80
java -jar netforce.jar brute -t 192.168.86.28 -un thatcher -pw src/main/resources/SSH_Pass.txt -w 250
java -jar netforce.jar brute -t 192.168.86.57 -p 2222 -un src/main/resources/SSH_User.txt -pw root
java -jar netforce.jar brute -t 192.168.86.28 -un src/main/resources/SSH_User.txt -pw src/main/resources/SSH_Pass.txt
```

## License
- [MIT](https://choosealicense.com/licenses/mit/)
- Copyright 2020 © Thatcher Clough.
