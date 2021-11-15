# "Found" - Blath team - ZooHackathon VN 2021
"Found"'s functions sources code.
Blath team - ZooHackathon VN 2021.

## BACKEND
[Read more on Devpost!]()

### Report
- The main class for the report.
- Split to online report (facebook post, tiktok, . . .) and offline report (menu of a restaurant, animal trafficking, . . .)
- Contains:
  - Ip address or GPS address 
  - Location (get from IP/GPS)
  - Time (the right moment the report is began)

#### Ip / GPS address
- We can use API or app to get this.

#### Location
- In this case, our project using `GeoLite2` and `GeoIP2` to get the location from Ip address. 
  ```Java
  ip = InetAddress.getByName(ipAddress);
        hostname = ip.getHostName();

        this.ip_address = ip.getHostAddress();
        this.host_name = hostname;

        File database = new File("GeoLite2/GeoLite2-City_20211109/GeoLite2-City.mmdb");
        DatabaseReader reader = new DatabaseReader.Builder(database).build();

        CityResponse response = reader.city(ip);
        Country country = response.getCountry();
        City city = response.getCity();

        Location location = response.getLocation();

        this.location = country.getName() + " - "
                + city.getName() + " - "
                + "X:" + location.getLatitude() + ", "
                + "Y:" + location.getLongitude();
  ```
- We can use another method to get location from GPS! (the same)

#### Time
- It's easy and very fast to get the time.
- In this case, our project using `LocalDateTime` in Java:
  ```Java
  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.time = dtf.format(now);
  ```

### Online Report
- Include three main attributes:
  - The online platform 
  - The content of the report
  - The object that the report is reported.
- There are two ways to report something online:
  - From a screenshot
  - From a url

#### Online platform
- The easiest way is told the user to identify it for us.
- We can use Goole API for this but it's not efficiency.

#### Content
- In this case, our project using `Tess4J` in Java to read the text in a picture:
  ```Java
  public static String query(String imageLocation, String language) {
        ITesseract instance = new Tesseract();
        instance.setLanguage(language);
        instance.setOcrEngineMode(1);
        instance.setDatapath("tessdata/");
        try
        {
            BufferedImage image = ImageIO.read(new File(imageLocation));
            String imgText = instance.doOCR(image);
            return imgText;
        }
        catch (TesseractException e)
        {
            e.getMessage();
            return "Error while reading image";
        }
        catch (IOException e)
        {
            e.getMessage();
            return "Error file";
        }
    }
  ```
- If the report is reported from an url, we can use facebook `Graph Facebook API` to get detail of the post, . . .

#### Object
- It's one of the hardest things in this project to make. 
- We have researching and found that we can use [`Google Vision AI`](https://cloud.google.com/vision) or [`Azure Machine Learning (Microsoft)`](https://azure.microsoft.com/en-us/services/machine-learning/) for identify the animals and object in the picture.
- In this case we have made a function to match template of two pictures (source and template) to find the template picture in the source by using `OpenCV` in Java:
  ```Java
  public static void match(String filePath, String sourceFileName, String templateFileName) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat source=null;
        Mat template=null;
        source=Imgcodecs.imread(filePath+sourceFileName);
        template=Imgcodecs.imread(filePath+templateFileName);

        Mat outputImage=new Mat();
        int machMethod=Imgproc.TM_CCOEFF;
        //Template matching method
        Imgproc.matchTemplate(source, template, outputImage, machMethod);

        MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        Point matchLoc=mmr.maxLoc;

        //Draw rectangle on result image
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(255, 255, 255));

        Imgcodecs.imwrite(filePath+"match"+sourceFileName, source);
    }
  ```
  The function will create a new picture has the position of the template picture in the source picture.
 
### Mapping the reports
- We have researching and found that we can use [`Google Maps JavaScript API`](https://developers.google.com/maps/documentation/javascript/overview) to mapping.
- By having a database (like MySQL) to store all the reports and then using `JavaScript` with that API to mapping it onto a web sites.

### Important note
- Our project is solid simple and it's very easy to develop.
- We have reasearched and found all the solution (in technology) as much as we can.
- We hope that our solution and notes may help ending the wildlife trafficking!

_**Thank you for reading!**_
