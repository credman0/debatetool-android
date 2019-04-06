# debatetool-android
Companion app for debatetool. For now at least it gives read-only access to the database, to avoid having to worry about versioning conflicts coming from people's phones.

## Screenshots

![Block](githubusercontent.com/credman0/debatetool-android/master/wiki/block.png?raw=true "Block")
![Browsing](githubusercontent.com/credman0/debatetool-android/master/wiki/tree.png?raw=true "Browsing")
![Card](githubusercontent.com/credman0/debatetool-android/master/wiki/card.png?raw=true "Card")

## Building
This requires you to first install the mongodb android driver into your local gradle repository, see [here](https://github.com/credman0/mongo-java-driver). Then you can build with ```./gradlew build```, and use adb (or other ways) to deploy to your device.
