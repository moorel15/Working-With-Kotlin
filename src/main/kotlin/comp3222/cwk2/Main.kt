package comp3222.cwk2
import java.io.File
import java.io.FileNotFoundException

fun loadFile(file:String): List<String> {
    val data = mutableListOf<String>()
    File("data/${file}").readLines().forEach { data.add(it) }
    return data
}

fun loadDataSet(file: String): DataSet {
    val dataSet = mutableListOf<DataRecord>()
    var data = loadFile(file)
    var x = 4
    var year = 0
    var month = 0
    var maxDeg = 0.0f
    var minDeg = 0.0f
    var afDays = 0
    var rainMm = 0.0f
    var sunHrs = 0.0f
    while(x < data.size) {
        var currentRecordString = data[x].trim()
        currentRecordString = currentRecordString.replace("\\s+".toRegex(), " ")
        val currentRecord = currentRecordString.split(" ")
        for(i in currentRecord) {
            val index = currentRecord.indexOf(i)
            when (index){
                0 -> year = currentRecord[index].toInt()
                1 -> month = currentRecord[index].toInt()
                2 -> maxDeg = currentRecord[index].toFloat()
                3 -> minDeg = currentRecord[index].toFloat()
                4 -> afDays = currentRecord[index].toInt()
                5 -> rainMm = currentRecord[index].toFloat()
                6 -> sunHrs = currentRecord[index].toFloat()
            }
        }
        val record = DataRecord(year, month, maxDeg, minDeg, afDays, rainMm, sunHrs)
        dataSet.add(record)
        x++
    }

    val dataSetInstance = DataSet(dataSet)
    return dataSetInstance
}

fun checkFile(file: String, argCount: Int): Boolean {
    val data = loadFile(file)
    if(data.size >= 4) {
        if(argCount == 1) {
            println("Station: " + data[0])
        }
        var det = data[1].split(" ", ",")
        for(i in det) {
            var ind = det.indexOf(i)
            if(argCount == 1) {
                when {
                    det[ind].equals("Lat") -> println("Latitude: " + det[ind+1])
                    det[ind].equals("Lon") -> println("Longitude: " + det[ind+1])
                    det[ind].equals("metres") -> println("Elevation: " + det[ind-1] + " m")
                }
            }
        }
        if((data.size - 4) <= 0) {
            println("Header of file was correct, however no data given.")
            return false
        }else {
            if(argCount == 1) {
                println("Number of records: " + ((data.size - 4).toString()))
            }
            return true
        }
    }else {
        println("Bad file presented, please input another file.")
        return false
    }
}

fun main(args: Array<String>) {
    if(args.size == 1) {
        try {
            val file = args[0]
            if (checkFile(file, 1)) {
                val data = loadDataSet(file)
                if(data.checkForErrors()) {
                    println("Years spanned: " + data.getYears())
                    println("Wettest year: " + data.getWettestYear())
                    println("Driest year: " + data.getDriestYear())
                    println("Driest Month: " + data.findDriestMonth())
                    println("Wettest Month: " + data.findWettestMonth())
                }
            }
        }catch (e: FileNotFoundException) {
            println("File not found in directory, please enter a valid file.")
        }
    }else if(args.size == 2) {
        try {
            val file = args[0]
            val year = args[1]
            if(checkFile(file, 2)) {
                val data = loadDataSet(file)
                var figures = data.getYearData(year.toInt())
                for(i in figures) {
                    var bar = i.rainMm.toInt() / 3
                    println("   " + data.getMonth(i.month) + " (${i.rainMm}): " + "#".repeat(bar))
                }
            }
        }catch (e: FileNotFoundException) {
            println("File not found in directory, please enter a valid file.")
        }
    }else {
        println("Incorrect number of arguments given. Please input 1 argument of the file name you wish to analyse.")
    }
}
