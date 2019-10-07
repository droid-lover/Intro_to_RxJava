# Reactive_Programming RxJava

"Introduction To Reactive Programming {RxJava, RxAndroid}"




### What is Reactive Programming?
  `well, it is event-based asynchronous programming ,`
  
  `we can create asynchronous data stream and transform the data on any thread and we can consume this data by an Observer on    any thread.`
  
  
### What is the need of Reactive Programming?

  `Main need of Reactive Programming is to improve user experience and to make our application more responsive.Reactive Programming uses asynchronous approach in which every task runs on its own thread and multiple tasks can run simultaneously and using it we can avoid blocking our app's main thread.`
  
  
  /*Reactive programming is programming with asynchronous data streams.*/
  
  
 ### What is RxJAVA?
  `RxJava is a Java VM implementation of Reactive Extensions.
  where we can create asynchronous data stream on any thread, transform it and these asynchronous data streams can be consumed  by Observers on any thread. `
  
 ### What is RxAndroid?
 `It is specific to Android Platform with some more added classes on top of RxJava.`
 
 `eg.) Schedulers are introduced in RxAndroid and it is more important to support Multithreading in android apps.`
  
 ### RxJava Concepts :- 
 `There are 2 main components in RxJAVA `
 
      1. Observable.
      2. Observer.
      
#### Observable   ≈ An Observable emits the data stream
#### Observer     ≈ An Observer receives the data emitted by Observable

`Multiple Observers can subscribe to a single Observable. and this connection between Observable and Observer is called as Subscription.`

`and to decide the thread on which Observable should emit the data and on which Observer should receives the data i.e background or main thread etc. we have Schedulers`

`another thing is Operator which modifies the data emitted by Observable before an observer receives them.(this data conversion can be done as per our requirements.`



                           -----   by Sachin   -----    HAPPY CODING :) 
                           
                           
 ### Some Interesting points (overall)--

1.Difference between Observable.from() and Observable.just() —Observable.just() emits only once whereas Observable.from()emits n times

2.Range operator creates an Observable that emits a range of sequential integers. it takes two arguments: the starting number and length.

3.Timer Operator creates an Observable that emits one particular item after a span of time.

4.Repeat allows emitting a particular item or sequence of items repeatedly. We can pass the number of repetitions also.
