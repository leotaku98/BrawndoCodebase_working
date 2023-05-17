# Assignment 2: Design Patterns

## Solutions to the Key Issues

### RAM Issue

#### Patterns used: Flyweight+Singleton
- FlyweightFactory:  FlyweightFactory.java under products package
- Singleton: FlyweightFactory.java
- Concrete Flyweight: "Map<Integer, double[]> flyweights" inside FlyweightFactory.java. double[] is the flyweight, Integer is the key to get the flyweight.
- Shared states: the integer key in "Map<Integer, double[]> flyweights"

##### Solution Summary
- I use flyweight to reduce RAM use, by adding FlyweightFactory.java and creating dataKey in ProductImpl.java
- Because I can only modify ProductImpl, I create FlyweightFactory and use Singleton Pattern to provide global access to the Factory.
- The factory has a map of <int, double[]>, the double[] is the data for factory, and the int is key to retrieve data.
- Product will store key to the data instead of data. 
- When given data, Product will check if data already exist in flyweight factory. If so, Product will store the key to that data. If not, Product will add that data to flyweight, flyweight will generate new key to that data and pass the key back to Product to store.
- Note: I decide not to make Manufacturating data with flyweight, beacause it's declared final. And the map key-value pair has the risk to be modified (change value, keep the same key), making it not final.
- Possible drawback. I make assumption that all data is  type double[]. Because this is what they currently look like in the code ╮(╯▽╰)╭
##### Solution Benefit
- Product now stores key(int) instead of data(doubble[50000]), reduce a lot of RAM use.
- If products' data has same value, they will store the same key, instead of both storing same double[50000] data, furthuer reducing storage use.


### Too Many Orders

#### Alternative Solution (400 words max)

##### Solution Summary

The solution summary goes here, you should describe what changes you have made to the codebase, be specific to the classes and methods you changed and how.

##### Solution Benefit

How did you solution solve the problem, be brief.

### Bulky Contact Method
#### Patterns used: Strategy Pattern
- Strategies are different ways to send invoices.
- The strategy classes are listed under contactsenders package. ContactSender.java is the interface, and the rest are concrete strategies.
##### Solution Summary
- There is a strategy interface called ContactSender.java with sendInvoice() method, and each type of message implements the interface and create a concrete strategy class.
- The specific strategy is choosen at the SPFEAFacade.java in method finaliseOrder(), according to message type.
- And in ContactHandler.java. We can have a simple for loop to loop over the ContactSender and execute sendInvoice().

##### Solution Benefit
This can reduce the complexity of the ContactHandler.java. When we have new contactSender, we can easily extend it by creating a new concrete stratgy class for that type, and no need to make changes to ContactHandler.java


#### Patterns used: Unit of Work
##### Solution Summary
- I added finalOrder attribute in SPFEAFacade. Whenever there's a update to order, the changes to the order will be applied to finalOrder instead of saving to database. For example:
  TestDatabase.getInstance().saveOrder(token, order); // not this
-
- When order is finalized(finaliseOrder method is called), we save the finalOrder to the database.
- For each ProductImpl, there's a ProductValueObject attribute. The valueObject will be used to compared across different product.
- I override euqals and hash method in ProductImpl with valueObject. Now ProductImpl can be compared with ==, instead of comparing each field.

##### Solution Benefit
now comparison can be done with "==" instead of comparing each attributes.



### System Lag

#### Patterns used: LazyLoad
- inside CustomerImpl.java
##### Solution Summary
- Current implementation of CustomerImpl is to retrieve all data of the customer from database once the customer is created.
- I use lazyload, so the CustomerImpl will only load the data when the data is needed, instead right from the creation.
- More specifically, I move TestDatabase.getInstance().getCustomerField from constructor to get method.

##### Solution Benefit
Reduce the time for getting Customer data.

### Hard to Compare Products

#### Patterns used: ValueObject
-value object: ProductValueObject.java under products package.
##### Solution Summary
- I added ProductValueObject.java. This valueObject will store and hash the info of Product(keys and Names) for comparison.
- For each ProductImpl, there's a ProductValueObject attribute. The valueObject will be used to compared across different product.
- I override euqals and hash method in ProductImpl and compare with valueObject. Now ProductImpl can be compared with ==, instead of comparing each field.

##### Solution Benefit
now comparison can be done with "==" instead of comparing each attributes.

### Slow Order Creation


## Notes About the Submission