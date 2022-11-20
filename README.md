# Exercise 3: Maternity Hospital Journal

The purpose of this exercise is to apply various algorithms 
on collections using the methods of the Collections class.

Duration – **45** minutes.

## Description

In this task, you will simulate the work of the maternity hospital 
journal during a week. The internal structure of the journal is 
a collection of the `Map` type, where the key is the weekday 
(for example, an enum of the `WeekDay` type), and the value is a list 
of babies born during this week (for example, objects of the `Baby` type).

The `Baby` class describes a baby with the following characteristics: 
`name`, `weight`, `height`, `gender`, and `birth` time. All 
characteristics are passed to the constructor to create an object. 
You must not change this class.

Please note that once a birth journal has been created and filled in, 
it cannot be changed. This needs to be guaranteed.

Now, please proceed to the `BirthJournalManagementImpl` class, 
which implements the `BirthJournalManagement` interface, and provide 
implementations of the following methods:
 
* `boolean addEntryOfBaby(WeekDay day, Baby baby)` \
Adds an entry of the specified baby to the journal. Returns `true` 
if the addition was successful, and, if not, `false`
* `void commit()` \
Makes the journal immutable
* `int amountBabies()` \ 
Returns the number of babies born in a week
* `List<Baby> findBabyWithHighestWeight(String gender)` \
Finds the baby of the specified gender with the highest `weight`. 
If there are multiple babies, sorts them alphabetically by `name`
* `List<Baby> findBabyWithSmallestHeight(String gender)` \
Finds the baby of the specified gender with the smallest height. 
If there are multiple babies, sorts them in ascending order of `weight`
* `Set<Baby> findBabiesByBirthTime(String from, String to)` \
Finds а babies born in the specified period of time

Details:
* The time of birth of babies is given as a `String` in the form "hour:minute".
* For implementations of all methods, it is guaranteed that the 
parameters passed to them are correct.
* You can add any private methods to the `BirthJournalManagementImpl` class.

### Restrictions

You may not use lambdas or streams while doing this task.
