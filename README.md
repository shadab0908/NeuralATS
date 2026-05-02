# ⚡ NEURAL ATS: AI-Powered Resume Logic Engine

**NEURAL ATS** is a high-performance, full-stack recruitment tool designed to bridge the gap between static resumes and dynamic job requirements, the application moves beyond simple keyword matching to perform deep semantic "Neural Scans," providing job-seekers with professional-grade ATS compatibility reports.

---

### 🚀 Core Functionalities
*   **Dynamic JD Comparison**: Analyzes PDF resumes against user-provided Job Descriptions (JD) in real-time.
*   **Neural Feedback**: Generates automated match percentages, identifies critical skill gaps, and provides 3 specific professional improvement directives.
*   **Cyberpunk Dashboard**: A custom-designed, dark-themed UI featuring glassmorphism, neon-glow aesthetics, and responsive layout.
*   **Real-time Rendering**: Utilizes **Marked.js** to convert raw LLM Markdown responses into clean, human-readable directives.

### 🛠️ Technical Stack
*   **Backend**: Java 21, Spring Boot 3.2 (MVC Architecture).
*   **AI Orchestration**: Google Gemini 2.5 Flash (Generative AI).
*   **Binary Processing**: Apache PDFBox for robust PDF text extraction.
*   **Data Handling**: Jackson for advanced nested JSON parsing.
*   **Frontend**: Thymeleaf, JavaScript (Fetch API), Bootstrap 5.

### 🧩 System Architecture
The project follows a modular **Controller-Service-Utility** pattern to ensure clean separation of concerns and production-ready scalability:
1.  **Controller Layer**: Manages RESTful endpoints and multi-part form data (file + text).
2.  **Service Layer**: Handles business logic and coordinates communication with the Gemini AI model.
3.  **Utility Layer**: Dedicated tools for binary PDF parsing and text sanitization.

### ⚠️ Challenges & Solutions
During development, we navigated several technical hurdles:
*   **JSON Parsing Logic**: Solved the "undefined" feedback issue by implementing a robust `ObjectMapper` logic to navigate the nested Gemini API response structure.
*   **Dynamic Analysis**: Shifted from hardcoded keyword matching to a dynamic prompt-based system, allowing for 1:1 matching against any unique Job Description.
*   **UI/UX**: Integrated **Marked.js** to ensure AI-generated Markdown suggestions were rendered with professional formatting rather than raw text blocks.

### 🔧 Installation & Setup
1. **Clone the repository**:
   ```bash
   git clone [https://github.com/shadab0908/NeuralATS.git](https://github.com/shadab0908/NeuralATS.git)
Configure API Key:
Add your Gemini API Key to src/main/resources/application.properties:

Properties
gemini.api.key=YOUR_API_KEY_HERE
Build and Run:

Bash
mvn clean install
mvn spring-boot:run


Developed by Shadab Javed Mulla as a project to demonstrate Java capabilities and AI integration.
