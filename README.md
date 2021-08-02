# spring-boot-foodetector

SPRİNG-BOOT,LOMBOK,VALİDATİON,EXCEPTİON-HANDLING,MYSQL


We can find new food/soup/desert,save,delete and upgrade with this project.

*Entity and MaterialEntity*
->for table

*Request Class*
->about info that  came from client and We use it while some knowledge are changed in data 

*Response Class*
-> it answer to inform client and we usually return it while answer the request

*Service*
->for methods, We wrote all necessary work in it

--getAll();     to see all object (only object name ,not materials)
--updateName();     to update object name(only object name, will be added soon for materials)
--delete();     to delete object together object materials
--newDesert();      to create new object together object materials
