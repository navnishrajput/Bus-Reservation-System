# Bus Reservation System 🚌

A comprehensive Java-based bus reservation system with file-based data storage and management capabilities.

## 📁 Project Structure

navnishrajput-bus-reservation-system/

├── 📊 buses.csv # Bus data storage

├── 📋 bookings.csv # Booking records storage

├── ⚙️ BusReservationSystem.iml # Project configuration

└── 📁 src/

├── 📊 buses.csv # Source bus data

├── 🔧 module-info.java # Module declaration

└── 📁 com/

└── 📁 bus/

├── 📁 main/

│ └── 🎮 BusApp.java # Main application class

├── 📁 model/

│ ├── 🎫 Booking.java # Booking entity class

│ ├── 🚌 Bus.java # Bus entity class

│ └── 👤 Passenger.java # Passenger entity class

└── 📁 service/

├── 🚌 BusService.java # Bus management service

├── 💾 FileService.java # File handling service

└── 📈 ReportService.java # Reporting service

## 🏗️ Architecture Overview

### 🎯 Core Components

#### **[🎮 Main Application](/src/com/bus/main/BusApp.java)**
- **Purpose**: Application entry point and user interface
- **Features**:
  - Main menu system
  - User interaction handling
  - Service coordination

#### **[📦 Model Classes](/src/com/bus/model/)**
- **🚌 Bus.java**: Bus entity with properties and methods
- **🎫 Booking.java**: Booking entity with reservation details
- **👤 Passenger.java**: Passenger information management

#### **[🛠️ Service Layer](/src/com/bus/service/)**
- **🚌 BusService.java**: Bus operations and management
- **💾 FileService.java**: CSV file read/write operations
- **📈 ReportService.java**: Reporting and analytics

## 🚀 Features

### ✅ Core Functionality
- **Bus Management** 🚌 - Add, view, and manage buses
- **Seat Reservation** 💺 - Book and cancel seats
- **Passenger Registration** 👤 - Manage passenger details
- **Booking System** 🎫 - Complete reservation workflow

### 📊 Data Management
- **CSV File Storage** 💾 - Persistent data storage
- **Data Import/Export** 📤 - Easy data management
- **Backup System** 🗂️ - Data safety and recovery

### 📈 Reporting
- **Booking Reports** 📋 - Reservation summaries
- **Bus Utilization** 📊 - Capacity and usage analytics
- **Revenue Tracking** 💰 - Financial reporting

## 🛠️ Technical Details

### 🔧 Technologies Used
- **Java** ☕ - Core programming language
- **CSV Files** 📄 - Data persistence
- **Object-Oriented Design** 🏗️ - Clean architecture
- **Modular Programming** 🧩 - Organized code structure

### 💾 Data Storage

📁 Data Files:

├── buses.csv - Bus information (ID, route, capacity, etc.)

└── bookings.csv - Booking records (passenger, bus, seat, date, etc.)

## 🎯 How to Use

### 📥 Installation
1. Clone or download the project
2. Ensure Java is installed on your system
3. Open in your preferred Java IDE

### 🚀 Running the Application
1. Navigate to `src/com/bus/main/BusApp.java`
2. Compile and run the application
3. Follow the menu prompts for operations

### 📋 Available Operations
- **View Buses** 👀 - Display available buses
- **Make Booking** ✅ - Reserve seats
- **Cancel Booking** ❌ - Remove reservations
- **View Reports** 📊 - Generate system reports
- **Manage Data** 🗃️ - Import/export operations

## 🔄 Workflow

1. **Bus Setup** → Add buses to the system
2. **Passenger Registration** → Create passenger profiles
3. **Seat Selection** → Choose available seats
4. **Booking Confirmation** → Complete reservation
5. **Report Generation** → View booking analytics

## 🎫 Booking Process
Select Bus → 2. Choose Seats → 3. Enter Passenger Details → 4. Confirm Booking

## 📊 File Formats

### 🚌 Buses CSV Structure

busId, busNumber, route, capacity, availableSeats, fare

### 🎫 Bookings CSV Structure
bookingId, passengerId, busId, seatNumber, bookingDate, status

## 🌟 Benefits

### 💡 For Administrators
- Efficient bus management
- Real-time seat availability
- Comprehensive reporting
- Easy data backup and restore

### 👍 For Passengers
- Easy booking process
- Seat selection flexibility
- Booking history access
- Quick cancellation options

## 🔮 Future Enhancements

- [ ] Web interface development
- [ ] Database integration
- [ ] Mobile application
- [ ] Payment gateway integration
- [ ] SMS/Email notifications
- [ ] Real-time tracking

---

**Built with ❤️ for efficient transportation management** 🚌

*Start managing your bus reservations effortlessly!* 🎯
