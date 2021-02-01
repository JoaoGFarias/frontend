Feature: Ordering of products on Demoblaze e-commerce

  Scenario Outline: Order a product
    Given I am on the Demoblaze Home page
    When select the "Sony vaio i5" product in the "Laptops" category
    And select the "Dell i7 8gb" product in the "Laptops" category
    And I go to the cart
    And I delete the "Dell i7 8gb" from the cart
    And I complete the order, for the customer with name "<name>" and credit card "<credit_card_number>"
    Then the order is confirmed
    #And the expected total purchase total is the sum of the ordered products
    Examples: Customer info
      | name    | credit_card_number  |
      | Ada     | 4111 1111 1111 1111 |
  #    | Grace   | 5500 0000 0000 0004 |
   #   | Lisa    | 3400 0000 0000 009  |
