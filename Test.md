Marketplace GUI

\*\*Server.java must be running before any of these tests are conducted. Once it is run it does not need to be run again.\*\*

All tests should be executed sequentially. Parentheses after the test name indicate whether it should be run on a client logged into a customer or seller account. In some test cases the step of clicking "ok" or clicking the x button is not stated; if there are no other buttons besides the "ok" and "x" on a GUI, it is assumed that you should click the "ok" button and then the "x" button if there is no "ok" button.

Test 1: User creates Seller account

Steps:

1. User runs Client.java
 Result: Login GUI appears
2. User clicks Create Account button
 Result: Create Account GUI appears.
3. Select Seller from the Account Type dropdown menu, and input Username, Password, and Name in their respective fields. Then click Create New Account.

Username: testaccount3

Password: testing

Name: TA
 Result: Confirmation message and welcome message appears and account is created. The main menu is now opened.

Test 2: Seller creates a new Market

1. User clicks "8. Create Market" button on the main menu.

Result: GUI appears prompting Market name.

1. User inputs Market Name
 Market Name: Test
 Result: Confirmation message, market is created and user is brought back to the main menu.

Test 3: Seller creates a new Product

1. User clicks "2. Edit Product from Store"
 Result: GUI appears prompting which action to be taken.
2. User selects "Create" from the dropdown menu
 Result: GUI appears prompting which market
3. User selects Test
 Result: GUIs appear prompting product info
4. User inputs product info
 Name: Oranges
 Description: You eat it
 Quantity: 100
 Price: 5.99
 Result: Product is created and confirmation message is shown
5. Repeat steps 1-3, repeat step 4 with the following input:
 Name: Apples
 Description: You eat it
 Quantity: 5
 Price: 10.99
 Result: Product is created and confirmation message is shown

 Repeat steps 1-3, repeat step 4 with the following input:
 Name: Cherries
 Description: You eat it
 Quantity: 200
 Price: 2.99
 Result: Product is created and confirmation message is shown

Test 4: Seller deletes a product

1. User clicks "2. Edit Product from Store"
 Result: GUI appears prompting which action to be taken.
2. User selects "Delete" from the dropdown menu
 Result: GUI appears prompting which market
3. User selects Test
 Result: GUIs appear prompting which product to delete
4. User selects "Cherries,You eat it,200,2.99"
 Result: Cherries product is deleted

Test 5: Seller edits a product

1. User clicks "2. Edit Product from Store"
 Result: GUI appears prompting which action to be taken.
2. User selects "Edit" from the dropdown menu
 Result: GUI appears prompting which market
3. User selects Test
 Result: GUI appears prompting which item, in a comma separated string with product info
4. User selects Apples
 Result: GUIs appear prompting product info
5. User inputs product info
 Name: Pears
 Description: You eat it
 Quantity: 40
 Price: 11.99
 Result: Product is edited and confirmation message is shown

Test 6: View Market (Seller)

1. User clicks "1. View Market"
 Result: GUI appears listing all products in the MarketPlace
 Should show Oranges and Pears from the Test market
2. User clicks x button and returns to main menu

Test 7: View Dashboard (Seller)

1. User clicks "4. View Dashboard"
 Result: GUI appears prompting input for a market
2. User inputs Test
 Result: GUI displays the list of products in that market and the number of times they have been purchased

Test 8: Export Products (Seller)

1. User clicks "6. Export Products"
 Result: GUI appears prompting file location and market name
2. User inputs file location and market name
 File location: exporttest.csv
 Market name: Test
 Result: Confirmation message appears, raw product data is exported to the file location, brought back to main menu

Test 9: Import Products (Seller)

1. Edit the exporttest.csv file and replace all instances of "Test" with "Import Test"
2. User uses the procedure in Test 2 to create a new market called "Import Test"
3. User clicks "5. Import Products"
 Result: GUI appears prompting file location and market name
4. User inputs file location and market name
 File location: exporttest.csv
 Market name: Import Test
 Result: Confirmation message appears, raw product data from provided csv file is imported to the specified market. Can be checked by using View Market or View Dashboard, as specified by tests 6 and 7.

Test 10: View Sales by Store (Seller)

1. User clicks "3. View Sales by Store"
 Result: GUI appears prompting market name
2. User inputs Test
 Result: GUI appears saying "There is no revenue generated from this store."

Test 11: More information (Seller)

1. User clicks "10. More information"
 Result: GUI appears with information on each option, User is brought back to the main menu

Test 12: Closing the application (Seller)

1. User clicks Exit or the X button on the top right of the window
 Result: Application closes

Test 13: Logging in (Seller)

1. User runs Client.java
 Result: Login GUI appears
2. User types in the Username and Password
 Username: testaccount3
 Password: testing
 Result: Welcome message appears, main menu appears

Test 14: User creates Customer account

Steps:

1. User runs Client.java
 Result: Login GUI appears
2. User clicks Create Account button
 Result: Create Account GUI appears.
3. Select Customer from the Account Type dropdown menu, and input Username, Password, and Name in their respective fields. Then click Create New Account.
 Username: customer5
 Password: testing
 Name: Tester
 Result: Confirmation message and welcome message appears and account is created. The dashboard is now opened.

Test 15: View Market (Customer)

1. User clicks "1. View Market"
 Result: GUI appears listing all products in the MarketPlace
2. User clicks x button and returns to main menu

Test 16: Search For Product (Customer)

1. User clicks "2. Search For Product"
 Result: GUI appears prompting for the type of search
2. User selects Name from the drop down
 Result: GUI appears prompting for the search text
3. User inputs Pear
 Result: GUI appears showing the Pear product from the Test market and the Import Test market
4. Repeat steps 1-3 with the following inputs for steps 2 and 3 respectively:
 Type of search: Description
 Search term: You

Result: GUI appears showing the Pears product from the Test market and the Import Test market, as well as the Oranges product from the Test market and the Import Test market
 Type of search: Store
 Search term: Test

Result: GUI appears showing the Pears product and the Oranges product from the Test
 Type of search: Description
 Search term: Testing
 Result: GUI will appear saying "no items found"

Test 17: Sort By Price (Customer)

1. User clicks "3. Sort By Price"
 Result: GUI appears showing a list of products, sorted in ascending order by price

Test 18: Sort by Quantity (Customer)

1. User clicks "4. Sort by Quantity"
 Result: GUI appears showing a list of products, sorted in ascending order by quantity

Test 19: View Dashboard (Customer)

1. User clicks "5. View Dashboard"
 Result: GUI appears with the following text:

Test has 2 products for sale.

1: [Pears, Test, You eat it, 40, 11.99]

2: [Oranges, Test, You eat it, 100, 5.99]

From Test, Tester has purchased the following products:

There have been no purchases from this store.
 Import Test has 2 products for sale.

1: [Pears, Import Test, You eat it, 40, 11.99]

2: [Oranges, Import Test, You eat it, 100, 5.99]

From Import Test, Tester has purchased the following products:

There have been no purchases from this store.

Test 20: Add Item to Shopping Cart (Customer)

1. User clicks "7. Add Item to Shopping Cart"
 Result: GUI appears prompting the user to select an item from the drop down menu
2. User clicks "Pears, Test, You eat it, 40, 11.99"
 Result: User is prompted to enter the quantity to buy
3. User enters "5"
 Result: Confirmation message is shown and items are added to the shopping cart. User is brought back to the main menu
4. Repeat steps 1-3, but for step 2 use "Oranges, Import Test, You eat it, 100, 5.99" as input and for step 3 use "100" as input.
 Result: same as step 3

Test 21: View Shopping Cart (Customer)

1. User clicks "10. View Shopping Cart"
 Result: GUI appears showing the users shopping cart.
 It should show Oranges with a quantity of 100 and Pears with a quantity of 5

Test 22: Remove from Shopping Cart (Customer)

1. User clicks "8. Remove Item from Shopping Cart"
 Result: User is prompted with GUI to select an item from the shopping cart and then the quantity to be removed
2. User selects Pears from the drop down menu, and inputs 3 as the number to remove

Run Test 19 again. This time the results should be:

 Test has 2 products for sale.

1: [Pears, Test, You eat it, 38, 11.99]

2: [Oranges, Test, You eat it, 100, 5.99]

From Test, Tester has purchased the following products:

There have been no purchases from this store.
 Import Test has 2 products for sale.

1: [Pears, Import Test, You eat it, 40, 11.99]

From Import Test, Tester has purchased the following products:

There have been no purchases from this store.

Run Test 21 again. This time it should show Oranges with a quantity of 100 and Pears with a quantity of 2.

Test 23: Purchase shopping cart (Customer)

User clicks "9. Purchase Items in Shopping Cart"
 Result: GUI appears with the following text

 Purchased 100 Oranges for 599.00 total
 Purchased 2 Pears for 23.98 total

 Shopping Cart purchased! Total cost: 622.98

 Run Test 21 again. This time the GUI should show the following text:
 There are no products found in your shopping cart.

 Run Test 19 again. This time the GUI should show the following text:

 Test has 2 products for sale.

1: [Pears, Test, You eat it, 38, 11.99]

2: [Oranges, Test, You eat it, 100, 5.99]

From Test, Tester has purchased the following products:

1: [Pears, Test, You eat it, 2, 11.99, customer5]
 Import Test has 1 products for sale.

1: [Pears, Import Test, You eat it, 40, 11.99]

From Import Test, Tester has purchased the following products:
 1: [Oranges, Import Test, You eat it, 100, 5.99, customer5]

Test 24: More information (Customer)

1. User clicks "11. More Information"

Result: User is shown a GUI with details about each option

Test 25: Export Purchase History (Customer)

1. User clicks "6. Export Purchase History"
 Result: User is shown a GUI prompting file location
2. User inputs "purchasehistory.txt"
 Result: Confirmation message is shown and purchase history is exported to "purchasehistory.txt". in the file it should show:

 Tester's Purchase History
 --------
 Purchased 2 Pears for
 11.99 each ($23.98) total from Test.
 Description: You eat it

 Purchased 100 Oranges for
 5.99 each ($599.00) total from Import Test.
 Description: You eat it

Test 26: Exit (Customer)

1. User clicks the "Exit" button
 Result: Main menu closes and program ends

Test 27: View Sales by Store (Seller)

1. User logs on using the procedure in Test 13, or switches to an already open Seller account window on the main menu.
 Result: Main menu appears
2. User clicks "3. View Sales By Store" and types "Import Test" in the resulting GUI
 Result: GUI appears with the following text
 1. Tester bought:
 100 Oranges for a total of 599.00
 Tester spent 599.00 dollars
 The total revenue made from the store is: 599.00

Test 28: Delete Market (Seller)

1. User clicks "9. Delete Market"
 Result: GUI appears prompting which market to delete
2. Select "Test" from the dropdown menu
 Result: Confirmation message appears, back to main menu.
 Run Test 6, Should show only Pears from Import Test
 Run Test 26 as a customer. Results should be the same

Test 29: View Shopping Carts (Seller)

1. Run the Client.java
2. Login with customer credentials:
 Username: customer5
 Password: testing
3. Click "7. Add Item to Shopping Cart" and select Pears from Import Test (should be the only option in the dropdown menu). Input 5 as the quantity.
4. Open seller window and click "7. View Shopping Carts" and select customer5 from the dropdown menu (should be the only option). Then click VIEW.
 Result: GUI appears showing customer5's shopping cart. Should have the Pear product from Import Test with 5 quantity.
