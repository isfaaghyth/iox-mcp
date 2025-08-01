object ExpenseFaker {

    fun create(): List<CreateExpenseRequestBody> {
        return listOf(
            // Day 8 - January 22, 2025
            CreateExpenseRequestBody("Excelso", "42000", "Food & Beverage", "1738425600"), // 08:00
            CreateExpenseRequestBody("Warteg Kharisma", "18000", "Food & Beverage", "1738436400"), // 11:00
            CreateExpenseRequestBody("Ranch Market", "267000", "Groceries", "1738447200"), // 14:00
            CreateExpenseRequestBody("OCBC Transfer", "425000", "Bank Transfer", "1738450800"), // 15:00
            CreateExpenseRequestBody("Gokana", "72000", "Food & Beverage", "1738458000"), // 17:00
            CreateExpenseRequestBody("Indomaret", "54000", "Groceries", "1738461600"), // 18:00

            // Day 9 - January 23, 2025
            CreateExpenseRequestBody("Kedai Kopi Kulo", "20000", "Food & Beverage", "1738512000"), // 08:00
            CreateExpenseRequestBody("Bakso Malang Karapitan", "22000", "Food & Beverage", "1738522800"), // 11:00
            CreateExpenseRequestBody("Foodhall", "189000", "Groceries", "1738533600"), // 14:00
            CreateExpenseRequestBody("UOB Transfer", "800000", "Bank Transfer", "1738537200"), // 15:00
            CreateExpenseRequestBody("Pepper Lunch", "85000", "Food & Beverage", "1738544400"), // 17:00

            // Day 10 - January 24, 2025
            CreateExpenseRequestBody("Kopiko", "15000", "Food & Beverage", "1738598400"), // 08:00
            CreateExpenseRequestBody("Soto Betawi H. Husein", "28000", "Food & Beverage", "1738609200"), // 11:00
            CreateExpenseRequestBody("Lotte Mart", "145000", "Groceries", "1738620000"), // 14:00
            CreateExpenseRequestBody("Maybank Transfer", "275000", "Bank Transfer", "1738623600"), // 15:00
            CreateExpenseRequestBody("A&W", "58000", "Food & Beverage", "1738630800"), // 17:00
            CreateExpenseRequestBody("Alfamidi", "37000", "Groceries", "1738634400"), // 18:00

            // Others
            CreateExpenseRequestBody("Geprek Bensu", "23000", "Food & Beverage", "1738684800"), // Next day 08:00
            CreateExpenseRequestBody("Richeese Factory", "47000", "Food & Beverage", "1738695600"), // 11:00
            CreateExpenseRequestBody("Transmart", "112000", "Groceries", "1738706400"), // 14:00
            CreateExpenseRequestBody("Panin Transfer", "550000", "Bank Transfer", "1738710000"), // 15:00
            CreateExpenseRequestBody("Sate Khas Senayan", "65000", "Food & Beverage", "1738717200"), // 17:00

            CreateExpenseRequestBody("Anomali Coffee", "35000", "Food & Beverage", "1738771200"), // Next day 08:00
            CreateExpenseRequestBody("Mie Ayam Tumini", "16000", "Food & Beverage", "1738782000"), // 11:00
            CreateExpenseRequestBody("Yogya", "98000", "Groceries", "1738792800"), // 14:00
            CreateExpenseRequestBody("HSBC Transfer", "650000", "Bank Transfer", "1738796400"), // 15:00
            CreateExpenseRequestBody("Ichiban Sushi", "95000", "Food & Beverage", "1738803600"), // 17:00

            // Yesterday - July 31, 2025
            CreateExpenseRequestBody("J.CO Donuts", "42000", "Food & Beverage", "1753948800"), // 08:00
            CreateExpenseRequestBody("Fore Coffee", "28000", "Food & Beverage", "1753952400"), // 09:00
            CreateExpenseRequestBody("Alfamart", "56000", "Groceries", "1753966800"), // 13:00
            CreateExpenseRequestBody("BCA Transfer", "500000", "Bank Transfer", "1753974000"), // 15:00
            CreateExpenseRequestBody("Ayam Bakar Wong Solo", "48000", "Food & Beverage", "1753981200"), // 17:00
            CreateExpenseRequestBody("Indomaret", "34000", "Groceries", "1753988400"), // 19:00

            // Today - August 1, 2025
            CreateExpenseRequestBody("Kopi Kenangan", "24000", "Food & Beverage", "1754035200"), // 08:00
            CreateExpenseRequestBody("Warteg Bahari", "18000", "Food & Beverage", "1754046000"), // 11:00
            CreateExpenseRequestBody("Superindo", "125000", "Groceries", "1754056800"), // 14:00
            CreateExpenseRequestBody("Mandiri Transfer", "750000", "Bank Transfer", "1754060400"), // 15:00
            CreateExpenseRequestBody("McDonald's", "52000", "Food & Beverage", "1754067600"), // 17:00
            CreateExpenseRequestBody("Circle K", "29000", "Groceries", "1754071200") // 18:00
        )
    }
}