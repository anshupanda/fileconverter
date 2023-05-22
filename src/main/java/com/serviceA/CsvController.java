


@RestController
public class CsvController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        try {
            // Read CSV data using OpenCSV
            CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
            List<String[]> rows = csvReader.readAll();
            csvReader.close();

            // Process each row
            for (String[] row : rows) {
                // Extract the phone number from the CSV row
                String phoneNumber = row[2]; // Assuming phone number is in the second column

                // Determine the country based on the phone number using regex
                String country = determineCountry(phoneNumber);

                // Will be deleted when full development is completed
                System.out.println("Phone Number: " + phoneNumber + ", Country: " + country);
            }

            return ResponseEntity.ok("CSV file processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process CSV file.");
        }
    }

    private String determineCountry(String phoneNumber) {

        Pattern cameroonPattern = Pattern.compile("\\(237\\)\\ ?[2368]\\d{7,8}$");
        Pattern ethiopiaPattern = Pattern.compile("\\(251\\)\\ ?[1-59]\\d{8}$");
        Pattern moroccoPattern = Pattern.compile("\\(212\\)\\ ?[5-9]\\d{8}$");
        Pattern mozambiquePattern = Pattern.compile("\\(258\\)\\ ?[28]\\d{7,8}$");
        Pattern ugandaPattern = Pattern.compile("\\(256\\)\\ ?\\d{9}$");


        if (cameroonPattern.matcher(phoneNumber).matches()) {
            return "Cameroon";
        } else if (ethiopiaPattern.matcher(phoneNumber).matches()) {
            return "Ethiopia";
        } else if (moroccoPattern.matcher(phoneNumber).matches()) {
            return "Morocco";
        } else if (mozambiquePattern.matcher(phoneNumber).matches()) {
            return "Mozambique";
        } else if (ugandaPattern.matcher(phoneNumber).matches()) {
            return "Uganda";
        }

        return "Unknown";
    }
}