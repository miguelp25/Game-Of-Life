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

##### `V2 Custom Mode`

In V2 I would like to have a user-friendly way of choosing dimensions and cell numbers. Also, I would like to implement a way of choosing between "random" mode and "pre-made" mode (like levels) and have many awasome levels to choose from.

<br/>
<br/>

##### `V1 Performance update` *(implemented)*

In V1 im trying to increase the app's performance. Right now in the default configuration the app is checking many millions of objects, and updating and re-drawing 700.000 objects / second (even more depending on the speed).
****************
I have changed the drawing method. Instead of drawing every alive cell and dead cell (700.000 cells in total), we only draw the alive ones in a black background (between 20.000 and 40.000 cells). (Yes, I know, how stupid am I?)
<br/>
Also, instead of checking the neighbours of every single cell (700.000 * 9 = 6.300.000), we check only the neighbors of the alive cells and its neighbors (40.000 * 9 * 9 = 3.240.000). Theoretically, if we have more than 80.000 alive cells we will start losing performance, but this is much better if we are planning pre-made, more elegant scenarios (like I do).

Yes, I am sure there's many ways to continue increasing the performance, like not checking previously checked cells, avoiding updating every single cell each iteration (700.000 cells) or only re-drawing cells that change it's status, but right now there's no such a performance problem as previously.

Finally, I've added a very very simple developer mode:
* With 'CTR I' you are able to display developer info.

![image](https://user-images.githubusercontent.com/30934149/123147441-56e8a180-d45f-11eb-951f-20ae9c3177f6.png)

<br/>
<br/>

##### `V0 Base Code` *(implemented)*

In V0 im trying to make the Game of life's basic logic and maybe a very simple Menu
* With 'CTR +' you are able to increase the speed.
* With 'CTR -' you are able to decrease and pause the speed.
* With space key you are able to pause/unpause.
* With escape key you are able to return to the main menu.
