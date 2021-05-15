### **Game-Of-Life**

A simple app in Java, that lets you interact with Conway's Game of Life

------

### **How to run it**
There's two ways of running this app:
* The simplest way is executing ./out/artifacts/Game_of_life_jar/Game-Of-Life.jar
* Running the static void main in Game_of_life.java


<br/>

### **Pre-View of the app:**
![image](https://user-images.githubusercontent.com/30934149/118011248-1a019900-b350-11eb-87f6-32a761a132da.png)
![image](https://user-images.githubusercontent.com/30934149/118014980-1112c680-b354-11eb-9601-527b0803979f.png)

<br/>
<br/>
<br/>

##### `V1 Performance update`

In V1 im trying to increase the app's performance. Right now in the default configuration the app is checking, updating and re-drawing 700.000 objects / second (even more depending of the speed).
But I'm sure there's no need for updating all the universe, we could just check the alive cells and the cells surrounding them and maybe obtain a performance increase.
<br/>
<br/>

##### `V0 Base Code`

In V0 im trying to make the Game of life basic logic and maybe a very simple Menu
* With 'CTR +' you are able to increase the speed.
* With 'CTR -' you are able to decrease and pause the speed.
* With space key you are able to pause/unpause.
* With escape key you are able to return to the main menu.
