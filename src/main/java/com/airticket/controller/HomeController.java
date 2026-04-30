package com.airticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Air Ticket Booking System</title>
                    <style>
                        body { font-family: 'Segoe UI', Arial, sans-serif;
                               background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                               margin: 0; padding: 40px; min-height: 100vh; }
                        .container { max-width: 900px; margin: auto; background: white;
                                     padding: 40px; border-radius: 12px;
                                     box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
                        h1 { color: #2c3e50; border-bottom: 3px solid #3498db; padding-bottom: 10px; }
                        h2 { color: #34495e; margin-top: 30px; }
                        .endpoint { background: #ecf0f1; padding: 12px; margin: 8px 0;
                                    border-left: 4px solid #3498db; border-radius: 4px; }
                        code { background: #2c3e50; color: #ecf0f1; padding: 3px 8px;
                               border-radius: 3px; font-family: 'Courier New', monospace; }
                        .badge { display: inline-block; background: #27ae60; color: white;
                                 padding: 4px 10px; border-radius: 12px; font-size: 12px; margin: 2px; }
                        ul { line-height: 1.8; }
                        .post { border-left-color: #27ae60; }
                        .put { border-left-color: #f39c12; }
                        .delete { border-left-color: #e74c3c; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>✈ Air Ticket Booking Management System</h1>
                        <p>A complete Air Ticket Booking system with full CI/CD pipeline integration.</p>

                        <h2>🛫 Flight API Endpoints:</h2>
                        <div class="endpoint"><code>GET /api/flights</code> - List all flights</div>
                        <div class="endpoint"><code>GET /api/flights/{id}</code> - Get flight by ID</div>
                        <div class="endpoint post"><code>POST /api/flights</code> - Add a new flight</div>
                        <div class="endpoint put"><code>PUT /api/flights/{id}</code> - Update flight</div>
                        <div class="endpoint delete"><code>DELETE /api/flights/{id}</code> - Delete flight</div>
                        <div class="endpoint"><code>GET /api/flights/search?source=X&destination=Y</code> - Search flights</div>
                        <div class="endpoint"><code>GET /api/flights/health</code> - Health check</div>

                        <h2>🎫 Booking API Endpoints:</h2>
                        <div class="endpoint"><code>GET /api/bookings</code> - List all bookings</div>
                        <div class="endpoint"><code>GET /api/bookings/{id}</code> - Get booking by ID</div>
                        <div class="endpoint post"><code>POST /api/bookings</code> - Book a ticket</div>
                        <div class="endpoint put"><code>PUT /api/bookings/{id}/cancel</code> - Cancel booking</div>
                        <div class="endpoint delete"><code>DELETE /api/bookings/{id}</code> - Delete booking</div>
                        <div class="endpoint"><code>GET /api/bookings/passenger?email=X</code> - Get by email</div>

                        <h2>🛠 DevOps Tools Integrated:</h2>
                        <span class="badge">Git</span>
                        <span class="badge">Jenkins</span>
                        <span class="badge">Maven</span>
                        <span class="badge">Docker</span>
                        <span class="badge">Kubernetes</span>
                        <span class="badge">Ansible</span>
                    </div>
                </body>
                </html>
                """;
    }
}
