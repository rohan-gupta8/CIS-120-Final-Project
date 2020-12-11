=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: grohan
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D arrays. I used 2D arrays to model the Game world as the game (RuneScape) is a tiled world
  This was implemented by setting positions in the array as integers (I used primitive data type
  to save memory) which marked positions of the various objects in the game.

  2. Collections. I used multiple collection implementations to store the player's inventory. 
  Most notably, I had a linkedlist of InventoryItems (for graphics state) and a linkedlist of 
  InventoryObjects (for internal state) stored in the InventoryView class. I used
  collections here because of the convenient add, remove, replace and clear methods which helped
  me model various states of the game efficiently and concisely.

  3. Inheritance/Subtyping: I created a Resource interface which modeled the interactable 
  'resources' in the game. These specifically had varied deplete methods and getState methods. 
  I also used the fact that they are subtypes of Resources in how I handled interactions between 
  objects and players, thereby invoking dynamic dispatch. I also inherited from GameObj and 
  significantly modified its fields.

  4. Complex game logic. For pathing, I implemented a variant of the breadth first search algorithm
  called Lee's algorithm to ensure that pathing was in straight lines and no diagonals. I also
  had to account for obstacles and had to path the player around them. Moreover, I implemented a 
  tick system which handled combat, movements, etc. every 60ms (just like RuneScape itself has a
  tick system). 

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  Anvil: GameObj to store the anvil (used to create the sword)
  Benjamin: stores the class of Sir Benjamin Franklin, an injured white knight who needs your help
  Fire: GameObj to store the fire (used to cook fish)
  Furnace: GameObj to store the furnace (to smelt the silver ore)
  Game: main game class
  GameCourt: the main game arena
  GameObj: a standard object in the main game
  InventoryItem: a standard object in the inventory
  InventoryObj: an enum class to store all possible inventory objects
  InventoryView: like game court, but for inventory display (extends JPanel)
  MyEditorKit: a helper class/toolkit taken from the internet to vertically align text
  Pathfinder: class to perform the BFS for routing the player's path properly
  Player: the actual player of the game
  Pond: GameObj to store the pond (to fish)
  Resource: interface implemented by the interactable objects that are not players/boss/benjamin 
  Rocks: GameObj to store the rocks (to mine from)
  
  
  


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  Graphics was harder than anticipated

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  Everything relevant is encapsulated properly. I would remove some redundant variables that I 
  created early on in the process if given the chance 


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  runescape.wiki for most images
  main background: https://opengameart.org/content/tower-defense-grass-background
  blue flame: https://in.pinterest.com/pin/607774912191186644/
  vertical text alignment: http://java-sl.com/tip_center_vertically.html
  names, content, ideas, and inspiration from www.runescape.com (owned by Jagex LTD)
