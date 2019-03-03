# Worldpay_merchant_offer
Allow a merchant to create a new simple offer.

In the description where it says "I price all my offers up front in a defined currency", it wasn't clear for me if the defined currency is defined up front as the price or it is a constant that never changes. I decided to give a getter and setter so it can be changed with an update or at initialization, but I gave an initial value to the field in the class.

The second thing was "Offers may also be explicitly cancelled before they expire" I didn't know if cancellation also meant to delete the offer. I went with just to indicate it with a flag, like with the expired attribute. I made a separate function where an offer could be cancelled without updating the whole offer, but it would have taken more time to branch the PUT function so I decided to stick to the simpler solution.

Additional idea was to make a function that deletes the expired offers and another one that deletes the cancelled ones (if they are not to be deleted after they are marked cancelled) and different sorting function that could give the offers back in a descending order based on price for example.

Running the application:
1)After cloning, a "maven install" has to be run
2)The .war file must be deployed on a server (I used Tomcat v8.0)
3)The API can be reached at http://localhost:8080/MerchantOffer/api/offer/ this list all the offers, making a POST to this address creates an offer. Example body: {"description": "Description", "price": 5000, "validityPeriod": "1"}
Getting a specified offer can be done by calling http://localhost:8080/MerchantOffer/api/offer/{id}
Updating an offer can be done the same way (Null check could be implemented here)
Calling DELETE without an {id} deletes all of the offers.
