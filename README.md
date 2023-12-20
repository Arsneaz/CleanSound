# CleanSound

An android app based on the spotify API to generete a list of tracks, create an albums an more, this app primaryly using Spotify API Interface to obtain the data and show into the app.

![image](https://github.com/Arsneaz/CleanSound/assets/96061442/aeeed2cc-8e1e-4b31-bbee-32b8376f868e)
![image](https://github.com/Arsneaz/CleanSound/assets/96061442/40a4cfff-fec8-4e07-a50b-26b26aeea6b2)
![image](https://github.com/Arsneaz/CleanSound/assets/96061442/c18d0e2f-987d-4032-a21f-f163c2c036c9)

## Features
- **Featured Playlist**: Get a Featured Playlist for you get the latest info of Playlist for you
- **Featured Tracks**: Get a Featured Tracks for you get the latest info of Tracks
- **Search Tracks**: Searching A Tracks Based on Spotify Api
- **Favorite Tracks**: Save A Favorite Tracks For You :)

## Explanation about the App
This app is using some TechStack as following
- This apps is using MVVVM (Model, View, ViewModel) as our nature Architecture of the platform
- This apps is using Firebase auth as our Authentication Provider
- This apps is using Room as media for storid profile user and tracks
- This apps is using Retrofit as our operation of API Service

## Getting Started
### Figma Prototype
Here's our Figma Prototype: [Link](https://www.figma.com/file/ywTNP6PPcdAtsIGtarcnoH/CleanSound-Mockup?type=design&node-id=0-1&mode=design&t=ElUIBhsIEuY5Mkt9-0)

### Contributing
Applikasi ini masih jauh dari kata sempurna, dan Segala bentuk kontribusi akan sangat dihargai, _feel free_ to fork this project and customized this project at _your own will_, don't forget to pull a request on this project and make this projects better.    

## Acknowledgments
This project makes use of this following _frameworks_ and _libraries_
- [Spotify Web Api](https://github.com/adamint/spotify-web-api-kotlin/blob/main/src/commonMain/kotlin/com.adamratzman.spotify/SpotifyApi.kt#L405) : Sebagai Spotify Wrapper untuk Mendapatkan Token Access
- [Retrofit](https://square.github.io/retrofit/) : Sebagai HTTP Client Service dalam Program kami
- [Chucker](https://www.google.com/search?q=chucker&oq=chucker&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTIGCAEQRRg80gEIMjAxMGowajmoAgCwAgA&sourceid=chrome&ie=UTF-8&safe=active&ssui=on) : HTTP Interceptor For Capturing the API Service
