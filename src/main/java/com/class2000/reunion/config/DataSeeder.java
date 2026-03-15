package com.class2000.reunion.config;

import com.class2000.reunion.model.*;
import com.class2000.reunion.repository.*;
import com.class2000.reunion.model.Initiative;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            MemberRepository memberRepo,
            PhotoRepository photoRepo,
            EventRepository eventRepo,
            DiscussionPostRepository postRepo,
            NewsRepository newsRepo,
            TeacherRepository teacherRepo,
            InitiativeRepository initiativeRepo
    ) {
        return args -> {
		/*
            // ── Members (seed only if empty) ──────────────────────────────
            if (memberRepo.count() == 0) {
                memberRepo.save(Member.builder().firstName("Aarav Sharma").currentCity("Bangalore").currentJob("Software Engineer").bio("Loving tech and coffee!").build());
                memberRepo.save(Member.builder().firstName("Priya Nair").currentCity("Mumbai").currentJob("Doctor").bio("Passionate about healthcare.").build());
                memberRepo.save(Member.builder().firstName("Rohan Mehta").currentCity("Delhi").currentJob("Entrepreneur").bio("Building the next big thing.").build());
                memberRepo.save(Member.builder().firstName("Divya Rao").currentCity("Hyderabad").currentJob("Teacher").bio("Inspiring young minds every day.").build());
                memberRepo.save(Member.builder().firstName("Karthik Iyer").currentCity("Chennai").currentJob("Architect").bio("Designing spaces that matter.").build());
            }

            // ── Teachers (seed only if empty) ─────────────────────────────
            if (teacherRepo.count() == 0) {
                teacherRepo.save(Teacher.builder()
                        .firstName("Rajesh Krishnamurthy")
                        .subject("Chemistry").designation("Head of Science Department")
                        .currentStatus("Retired")
                        .bio("A legend in the chemistry lab! Famous for making science exciting — and occasionally terrifying. Students still remember the great evacuation of 1999.")
                        .build());

                teacherRepo.save(Teacher.builder()
                        .firstName("Meena Subramaniam")
                        .subject("Mathematics").designation("Class Teacher - 10th Grade")
                        .currentStatus("Still Teaching")
                        .bio("Turned the most feared subject into something beautiful. Her patience and clarity made mathematics click for hundreds of students over the years.")
                        .build());

                teacherRepo.save(Teacher.builder()
                        .firstName("Samuel DSouza")
                        .subject("English Literature").designation("Senior Teacher")
                        .currentStatus("Retired")
                        .bio("His Shakespeare readings gave us all goosebumps. A man of eloquence and warmth who always had a story within a story.")
                        .build());

                teacherRepo.save(Teacher.builder()
                        .firstName("Lakshmi Venkatesh")
                        .subject("History & Civics").designation("Department Head")
                        .currentStatus("Retired")
                        .bio("She brought history to life. Whether it was the Mughal Empire or the Freedom Movement, she made us feel we were right there in the moment.")
                        .build());

                teacherRepo.save(Teacher.builder()
                        .firstName("Anand Pillai")
                        .subject("Physical Education").designation("Sports Coach")
                        .currentStatus("Still Teaching")
                        .bio("The man behind every medal and every sports day champion. His morning drills were tough, but his belief in every student was tougher.")
                        .build());
            }

            // ── Photos (seed only if empty) ───────────────────────────────
            if (photoRepo.count() == 0) {
                photoRepo.save(Photo.builder().title("Annual Day 1999").description("Our unforgettable annual day performance").imageUrl("https://picsum.photos/seed/annual/800/600").uploadedBy("Priya Nair").tags("annual,school,fun").build());
                photoRepo.save(Photo.builder().title("Sports Day 2000").description("Champions on the field!").imageUrl("https://picsum.photos/seed/sports/800/600").uploadedBy("Rohan Mehta").tags("sports,games").build());
                photoRepo.save(Photo.builder().title("Farewell Party").description("The last goodbye... or was it?").imageUrl("https://picsum.photos/seed/farewell/800/600").uploadedBy("Divya Rao").tags("farewell,memories").build());
                photoRepo.save(Photo.builder().title("Science Exhibition").description("Our award-winning science project.").imageUrl("https://picsum.photos/seed/science/800/600").uploadedBy("Karthik Iyer").tags("science,award").build());
            }

            // ── Events (seed only if empty) ───────────────────────────────
            if (eventRepo.count() == 0) {
                eventRepo.save(Event.builder().title("25-Year Grand Reunion").eventDate(LocalDateTime.of(2025, 12, 20, 18, 0)).location("Royal Orchid Hotel, Bangalore").description("Join us for a grand evening celebrating 25 years since we passed out of 10th grade. Dinner, music, memories!").organizer("Aarav Sharma").rsvpCount(42).build());
                eventRepo.save(Event.builder().title("Online Quiz Night").eventDate(LocalDateTime.of(2026, 3, 15, 20, 0)).location("Zoom (link TBD)").description("Test your memory of our school days in this fun trivia night!").organizer("Divya Rao").rsvpCount(18).build());
            }

            // ── Discussion Posts (seed only if empty) ─────────────────────
            if (postRepo.count() == 0) {
                postRepo.save(DiscussionPost.builder().author("Priya Nair").title("Does anyone remember Mr. Krishnamurthy's chemistry class?").content("I was thinking about the time he accidentally mixed the wrong chemicals and we all had to evacuate! That man was a legend. Who else has funny stories?").likes(15).build());
                postRepo.save(DiscussionPost.builder().author("Rohan Mehta").title("Where is everyone now?").content("It's been 25 years! I'm in Delhi running a small startup. Would love to hear what everyone is up to. Drop your city and what you're doing now!").likes(27).build());
            }

            // ── News (seed only if empty) ─────────────────────────────────
            if (newsRepo.count() == 0) {
                newsRepo.save(News.builder().title("Our Class of 2000 Website is LIVE!").content("After years of WhatsApp groups and scattered memories, we finally have a dedicated space for all of us. Welcome to our official Class of 2000 reunion portal!").author("Admin").category("ANNOUNCEMENT").build());
                newsRepo.save(News.builder().title("Priya Nair wins National Healthcare Award").content("We're proud to share that our very own Priya Nair has been awarded the National Excellence in Healthcare award for her work in rural medical outreach. Congratulations Priya!").author("Rohan Mehta").category("MILESTONE").publishedAt(LocalDateTime.now().minusDays(5)).build());
                newsRepo.save(News.builder().title("Memories of School Sports Day").content("We dug up some old photos from Sports Day 1999. Remember how hot it was? The 4x100 relay team almost didn't make it to the line! Those were the days.").author("Karthik Iyer").category("MEMORY").publishedAt(LocalDateTime.now().minusDays(10)).build());
            }

            // ── Giving Back Initiatives (seed only if empty) ──────────────
            if (initiativeRepo.count() == 0) {
                initiativeRepo.save(Initiative.builder()
                    .title("Vehicle Stand for Our School")
                    .description("Our class came together, pooled our resources, and built a dedicated vehicle stand for our beloved school. A small token of gratitude to the place that gave us everything.")
                    .type("infrastructure")
                    .badge("Built with love by Class of 2000")
                    .year("2024")
                    .build());

                initiativeRepo.save(Initiative.builder()
                    .title("Supporting Our Fallen Friends' Children")
                    .description("We remember our classmates who are no longer with us. Their children deserve every opportunity to thrive. Our class collectively raised funds and extended a helping hand.")
                    .type("support")
                    .badge("In memory of our dear classmates")
                    .year("2024")
                    .build());
            }
			*/
        };
    }
}
