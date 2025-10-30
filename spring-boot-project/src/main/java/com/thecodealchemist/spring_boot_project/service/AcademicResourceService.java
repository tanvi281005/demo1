package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.AcademicResourceRepository;
import com.thecodealchemist.spring_boot_project.dao.NotificationRepository;
import com.thecodealchemist.spring_boot_project.model.AcademicResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;  // ✅ import this

@Service
public class AcademicResourceService {

    private final AcademicResourceRepository resourceRepository;
    private final NotificationRepository notificationRepository;
    private final JdbcTemplate jdbcTemplate; // ✅ inject directly

    public AcademicResourceService(AcademicResourceRepository resourceRepository,
                                   NotificationRepository notificationRepository,
                                   JdbcTemplate jdbcTemplate) {
        this.resourceRepository = resourceRepository;
        this.notificationRepository = notificationRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Upload PDF resource
    public void uploadResource(Integer studentId, String course, String subjectCode,
                               AcademicResource.ResourceType type, MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("PDF file must be provided");
        }
        if (!file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        AcademicResource resource = new AcademicResource();
        resource.setStudentId(studentId);
        resource.setCourse(course);
        resource.setSubjectCode(subjectCode);
        resource.setResourceType(type);
        resource.setContent(file.getBytes());

        resourceRepository.save(resource);

        // ✅ Notify all subscribed users
        List<Map<String, Object>> subscribers =
                notificationRepository.findSubscribers(subjectCode, type.name());

        for (Map<String, Object> sub : subscribers) {
            int userId = (int) sub.get("user_id");

            jdbcTemplate.update(
                "INSERT INTO Notification (user_id, title, message) VALUES (?, ?, ?)",
                userId,
                "New Material Uploaded!",
                "A new " + type + " for " + subjectCode + " has been uploaded."
            );
        }

        notificationRepository.markAsNotified(subjectCode, type.name());
    }

    public List<String> fetchuniquesubjects() {
        return resourceRepository.fetchuniquesubjects();
    }

    public List<AcademicResource> findResourcesBySubjectAndType(String subject, AcademicResource.ResourceType type) {
        return resourceRepository.findBySubjectcodeAndResourceType(subject, type);
    }

    public AcademicResource getResourceById(int id) {
        return resourceRepository.findById(id);
    }

    public void saveNotificationSubscription(int userId, String subject, AcademicResource.ResourceType type) {
        notificationRepository.saveSubscription(userId, subject, type.name());
    }
    public List<Map<String, Object>> fetchUserNotifications(int userId) {
    String sql = "SELECT notification_id, title, message FROM Notification WHERE user_id = ? AND is_read = FALSE";
    List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, userId);

    // mark them as read immediately after showing
    jdbcTemplate.update("UPDATE Notification SET is_read = TRUE WHERE user_id = ?", userId);

    return list;
}

}
