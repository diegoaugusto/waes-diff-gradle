# WAES Assignment - Diff
This repository is the home of the assignment to develop a diff service.

## Quick Start
In order to run this project locally, it is necessary to download some tools (if you don't have them already) and proceed with the steps below.

### Tools
These set of tools were used during the development of this project and are the recommended tools. 
Other versions or different tools that do the same job were not tested and therefore may or may not not work properly. Feel free to try it and provide your feedback.

**Tool** | **Selected Option** | **Why it is used in this project?**
------------ | ------------- | -------------
Java :coffee: | Oracle 1.8.0_131 | ([Not anymore](https://twitter.com/java/status/910944824353628160)) the latest version of Java.
NoSQL Database | MongoDB 3.4.9 | It is faster to implement applications with it. It doesn't require a deep and formal data model design, with relationships, constraints and many other complexities. The development is simplified and entities coming from the database can directly be presented in the user interface.
Build Automation | Gradle 3.5 | It is a natural evolution from Ant and Maven and it is becoming a standard in the market. It follows a standard code structure similar to what Maven did, and known by many developers around the world. Compared with Maven, it has a better performance and it makes the process of building and testing the application easier and simpler to be define.
Application Server | WildFly 10.1.0 | It is open source and it provides a full Java Enterprise Edition (JEE) stack.  
IDE | Eclipse Oxygen Release (4.7.0) | For Java projects, it is the one I am more used to work with.


### Download and Installation
- Install [MongoDB](https://www.mongodb.com/) by following the steps provided in the [Download Page](https://docs.mongodb.com/master/administration/install-community/) according to the Operating System you use.
  - In a MacOS environment, execute `brew install mongodb` in the command line (you must have [Homebrew](https://brew.sh/) already installed).
  - After installation, the database can be started with the `mongod` command.
- [Gradle](https://gradle.org/) can be installed by following the steps provided in [their documentation page](https://gradle.org/install/) or you can install the [Eclipse Buildship plugin for Gradle](https://projects.eclipse.org/projects/tools.buildship) on your Eclipse instance.
- [Widlfly](http://wildfly.org/) can be downloaded and installed by following the steps available in their [download page](http://wildfly.org/downloads/). You will need to add it as a Runtime Environment on Eclipse later on.
- Download [Eclipse Oxygen](https://www.eclipse.org/downloads/) and whenever is asked, select the option for Java EE Developers.
- Clone this project to any folder you want in your file system.


As soon as all these tools are installed, follow the steps
- Open Eclipse
- Open the Eclipse Marketplace, search for _Gradle_ and install the _Buildship Gradle Integration 2.0_
- Import the `waes-diff-gradle` project that was cloned in a folder of your choice
- Right-click the project and select Gradle -\> Refresh Gradle Project
- Go to the Gradle Tasks tab and Run a build (it will download the dependencies, build and run all the unit tests). If you are in a command line environment, run `./gradlew build` from the project folder.
- Create a new server on Eclipse and set its type to be a WildFly 10.x. Add the `waes-diff-gradle` project to this server
- Start MongoDB in the command line `mongod`
- Start the WildFly server from Eclipse

## Using this application
Assuming the application is running on your host `localhost:8080`, the REST services listed below can be called by using any REST API client (browser plugin, code written in Java or in any other language).
Here are the available services:

Method | Path | Details
----- | ----- | ----- 
GET | _\<host\>_/waes-diff-gradle/v1/diff/*\<ID\>* | *\<ID\>* MUST be positive Integer value
POST | _\<host\>_/waes-diff-gradle/v1/diff/*\<ID\>*/*\<SIDE\>* | *\<ID\>* MUST be positive Integer value. *\<SIDE\>* has two possible values: **left** or **right**

### Detailed view of the services
Here you can see details about each service.

#### GET _\<host\>_/waes-diff-gradle/v1/diff/*\<ID\>*

- **200** When request is successful
```javascript
{
  "status": 200,
  "message": "Request successfully processed.",
  "diffObject":{
    "id": 1,
    "left": "YWJjZGVmZ2hpag==",
    "right": "MDEyZGVmZ2hpag=="
  }
}
```

- **404** Not Found, when there isn't a corresponding *\<ID\>* in the database or the *\<ID\>* is invalid
```javascript
{
  "status": 404,
  "error": "Id does not exist.",
  "exception": null,
  "suppressed":[]
}
```

- **405** Method is not allowed, when *\<ID\>* is empty
- **500** Internal server error has happened


#### POST _\<host\>_/waes-diff-gradle/v1/diff/*\<ID\>*/*\<SIDE\>*

- **Payload** body example
```javascript
{"data":"MDEyZGVmZ2hpag=="}
```

- **200** When request is successful
```javascript
{
  "status": 200,
  "message": "Request successfully processed.",
  "diffObject":{
  "id": 1,
    "left": "YWJjZGVmZ2hpag==",
    "right": "MDEyZGVmZ2hpag=="
  }
}
```

- **400** Bad Request. The payload data may not be a valid Base64 value.
```javascript
{
  "status": 400,
  "error": "This is not a valid Base64 encoded value.",
  "exception": "Last unit does not have enough valid bits",
  "suppressed":[]
}
```

- **404** Not Found. Invalid or non-existing ID
```javascript
{
  "status": 404,
  "error": "ID MUST be a positive Integer value.",
  "exception": null,
  "suppressed":[]
}
```

- **500** Internal server error has happened

### Suggestion of use
- Call POST _\<host\>_/waes-diff-gradle/v1/diff/1/left passing {"data":"YWJjZGVmZ2hpag=="} as the body of the request.
- Call POST _\<host\>_/waes-diff-gradle/v1/diff/1/right passing {"data":"MDEyZGVmZ2hpag=="} as the body of the request.
- Call GET _\<host\>_/waes-diff-gradle/v1/diff/1 and check its response


## How the code is organized


## Possible improvements for future iterations.
- Possibility to add new versions of the service API without impacting the existing version of the services.
- Accept binary data directly from an upload form. User could upload two files and compare them. Another possibility is to submit one audio file and compare left/right streams and identify if it is a mono (streams will be equal) or stereo audio file.
- One unique POST service to submit both left and right binary data.
- Provide User Interface to better test the services and manage the IDs.