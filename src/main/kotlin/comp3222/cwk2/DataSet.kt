package comp3222.cwk2

data class DataSet(var records: List<DataRecord>) {

    fun checkForErrors(): Boolean {
        var dataCorrect = true
        for(i in records) {
            if(i.year < 0) {
                dataCorrect = false
                println("\nError found in data.\nCategory: Year")
            }else if((i.month < 1) || (i.month > 12)) {
                dataCorrect = false
                println("\nError found in data.\nCategory: Month")
            }else if(i.afDays < 0) {
                dataCorrect = false
                println("\nError found in data.\nCategory: AF days")
            }else if(i.rainMm < 0.0) {
                dataCorrect = false
                println("\nError found in data.\nCategory: Rain mm")
            }else if(i.sunHrs < 0.0) {
                dataCorrect = false
                println("\nError found in data.\nCategory: Sun hours")
            }
        }
        return dataCorrect
    }

    fun findWettestMonth(): String {
        var result = ""
        var month: String
        var wettest = 0.0f
        for(i in records) {
            if(i.rainMm > wettest) {
                month = getMonth(i.month)
                result = "${month} ${i.year} (${i.rainMm} mm)"
                wettest = i.rainMm
            }
        }
        return result
    }

    fun findDriestMonth(): String {
        var result = ""
        var month: String
        var init = true
        var driest = 0.0f
        for(i in records) {
            if(init) {
                if(i.rainMm != 0.0f) {
                    init = false
                    driest = i.rainMm
                }
            }
            if(driest > i.rainMm) {
                month = getMonth(i.month)
                result = "${month} ${i.year} (${i.rainMm} mm)"
                driest = i.rainMm
            }
        }
        return result
    }

    fun getYears(): String {
        var result = ""
        var first = 0
        var last = 0
        var init = true
        for(i in records) {
            if(init) {
                if(i.year != 0) {
                    first = i.year
                    last = i.year
                    init = false
                }
            }
            if(i.year > last) {
                last = i.year
            }else if(i.year < first) {
                first = i.year
            }
        }
        result = "${first} to ${last}"
        return result
    }

    fun getWettestYear(): String {
        var current = 0.0
        var highRain = 0.0
        var highYear = 0
        var x = 0
        for(i in records) {
            x = i.month
            current+=i.rainMm
            if(x == 12) {
                if(current > highRain) {
                    highRain = current
                    highYear = i.year
                }
                current = 0.0
            }
        }
        var highRainFloat = highRain.toFloat()
        var result = "$highYear ($highRainFloat mm)"
        return result
    }

    fun getDriestYear(): String {
        var current = 0.0
        var lowRain = 0.0
        var lowYear = 0
        var x = 0
        var init = true
        for(i in records) {
            x = i.month
            current += i.rainMm
            if(x== 12) {
                if(init) {
                    lowRain = current
                    lowYear = i.year
                    init = false
                }
                if(current < lowRain) {
                    lowRain = current
                    lowYear = i.year
                }
                current = 0.0
            }
        }
        var lowRainFloat = lowRain.toFloat()
        var result = "${lowYear} (${lowRainFloat} mm)"
        return result
    }

    fun getYearData(year: Int): List<DataRecord> {
        var yearlyFigures = mutableListOf<DataRecord>()
        for(i in records) {
            if(i.year == year) {
                yearlyFigures.add(i)
            }
        }
        if(yearlyFigures.size == 0) {
            println("The year specified is not present in the file sent.")
        }
        return yearlyFigures
    }

    fun getMonth(month: Int): String {
        var monthString = ""
        when (month){
            1 -> monthString = "January"
            2 -> monthString = "February"
            3 -> monthString = "March"
            4 -> monthString = "April"
            5 -> monthString = "May"
            6 -> monthString = "June"
            7 -> monthString = "July"
            8 -> monthString = "August"
            9 -> monthString = "September"
            10 -> monthString = "October"
            11 -> monthString = "November"
            12 -> monthString = "December"
        }
        return monthString
    }
}