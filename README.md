# NetForce
NetForce: A network scanning and SSH brute forcing tool

## Features
NetForce can both scan a network for open ports and brute force attack an SSH server.

It can scan a network when given a range of IP addresses along with a port to scan for.
This will return all IP addresses in the given range with the given port open.

NetForce can also brute force attack an SSH server in attempt to obtain a password.
This requires a host address/name, port, user, and wordlist file containing passwords to try.
A sample wordlist file with common SSH passwords is provided in src/main/resources/SSH_Pass.txt.

## Requirements
- A Java JDK distribution must be installed and added to PATH with label JAVA_HOME.

## Compatibility
NetForce is compatible with both Windows and Linux.

## Installation
```
# clone NetForce
git clone https://github.com/ThatcherDev/NetForce.git

# change the working directory to NetForce
cd NetForce

# build NetForce with Maven
# for Linux run
chmod +x mvnw
./mvnw clean package

# for Windows run
mvnw.cmd clean package
```

Alternatively, you can download the jar from the [release page](https://github.com/ThatcherDev/NetForce/releases).

## Usage
```
java -jar netforce.jar
NetForce: A network scanning and SSH brute forcing tool (1.0)

Usage:
	java -jar netforce.jar [-h] [-v] [-s -t IPRANGE -p PORT -w TIMEOUT]
			       		   [-b -t HOST -p PORT -u USER -f FILE -w TIMEOUT]
Arguments:
	-h, --help		Display this message.
	-v, --version		Display current version.
	-s, --scan		Scan network for open ports.
	-b, --bruteforce	Brute force SSH server.
	-t, --target		Specify IP range when scanning, or host when brute forcing.
	-p, --port		Specify port to use. (Set to 22 if not specified)
	-u, --user		Specify user to brute force.
	-f, --file		Specify wordlist file to user when brute forcing.
	-w, --wait		Specify connection timeout in milliseconds. (Set to 300 if not specified)
```
### Examples of usage:
```
java -jar netforce.jar -s -t 192.168.86.1-255 -w 100
java -jar netforce.jar -s -t 192.168.1-5.1-255 -p 80
java -jar netforce.jar -b -t 192.168.86.28 -u thatcher -f src/main/resources/SSH_Pass.txt
java -jar netforce.jar -b -t 192.168.86.57 -p 2222 -u pi -f src/main/resources/SSH_Pass.txt -w 250
```

## License
- [MIT](https://choosealicense.com/licenses/mit/)
- Copyright 2019© ThatcherDev.
