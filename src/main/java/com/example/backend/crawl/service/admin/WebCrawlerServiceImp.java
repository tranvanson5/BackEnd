package com.example.backend.crawl.service.admin;

import com.example.backend.crawl.model.CrawlJob;
import com.example.backend.crawl.repository.CrawlJobRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class WebCrawlerServiceImp implements WebCrawlerService{
    @Autowired
    private CrawlJobRepository crawlJobRepository;
    @Scheduled(fixedRate = 60000*60*24)
    public void crawlWebsitePeriodicallyCareerlink() throws IOException {
        int page = 1;
        String baseUrl = "https://www.careerlink.vn/vieclam/list?page=";

        while (true) {
            String url = baseUrl + page;
            Document document = Jsoup.connect(url).get();

            Elements links = document.select(".job-link.clickable-outside");
            if (links.isEmpty()) {
                break;
            }
            for (Element link : links) {
                String stringLinkJobDetail = link.attr("abs:href");

                if (crawlJobRepository.existsByWebUrl(stringLinkJobDetail)) {
                    continue;
                }

                CrawlJob crawlJob = new CrawlJob();
                crawlJob.setWebName("career".toUpperCase());
                crawlJob.setWebUrl(stringLinkJobDetail);

                try {
                    Document documentDetail = Jsoup.connect(crawlJob.getWebUrl()).followRedirects(false).get();

                    Element imageUrl = documentDetail.select(".company-img.img-thumbnail.p-0.bg-white.border-0").first();
                    crawlJob.setImageUrl(imageUrl != null ? imageUrl.attr("abs:src") : null);

                    Element title = documentDetail.select(".job-title.mb-0").first();
                    crawlJob.setTitle(title != null ? title.text() : null);

                    Element company = documentDetail.select(".org-name.mb-2 span").first();
                    crawlJob.setCompany(company != null ? company.text() : null);

                    Element companyUrl = documentDetail.select(".org-name.mb-2 a").first();
                    crawlJob.setCompanyUrl(companyUrl != null ? companyUrl.attr("abs:href") : null);

                    Element address = documentDetail.select(".d-flex.align-items-start.mb-2").first();
                    crawlJob.setAddress(address != null ? address.text() : null);

                    Element salary = documentDetail.select(".d-flex.align-items-center.mb-2 .text-primary").first();
                    crawlJob.setSalary(salary != null ? salary.text() : null);

                    Element experience = documentDetail.select("div.d-flex.align-items-center.mb-2 > span").first();
                    crawlJob.setExperience(experience != null ? experience.text() : null);

                    Element recruitmentStartDate = documentDetail.select("div.date-from.d-flex.align-items-center .d-flex:has(span:contains(Ngày đăng tuyển))").first();
                    crawlJob.setRecruitmentStartDate(recruitmentStartDate != null ? recruitmentStartDate.text().replace("Ngày đăng tuyển", "").trim() : null);

                    Element recruitmentEndDate = documentDetail.select(".day-expired.d-flex.align-items-center span b").first();
                    if (recruitmentEndDate != null) {
                        if (recruitmentEndDate.text().equals("Hôm nay")) {
                            crawlJob.setRecruitmentEndDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        } else {
                            crawlJob.setRecruitmentEndDate(String.valueOf(
                                    LocalDate.now().plusDays(Integer.parseInt(recruitmentEndDate.text().replace("Ngày tới", "").trim()))
                                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                            ));
                        }
                    } else {
                        crawlJob.setRecruitmentEndDate(null); // Handle the case when recruitmentEndDate is null
                    }

                    Element description = documentDetail.select("#section-job-description").first();
                    crawlJob.setDescription(description != null ? description.text() : null);

                    Element skills = documentDetail.select("#section-job-skills").first();
                    crawlJob.setSkill(skills != null ? skills.text() : null);

                    Element typeJob = documentDetail.select(".d-flex.align-items-baseline.label.mb-3:has(.fa-inbox) .font-weight-bolder").first();
                    crawlJob.setTypeJob(typeJob != null ? typeJob.text() : null);

                    Element position = documentDetail.select(".d-flex.align-items-baseline.label.mb-3:has(.fa-layer-group) .font-weight-bolder").first();
                    crawlJob.setPosition(position != null ? position.text() : null);

                    Element education = documentDetail.select(".d-flex.align-items-baseline.label.mb-3:has(.fa-graduation-cap) .font-weight-bolder").first();
                    crawlJob.setEducation(education != null ? education.text() : null);

                    Element gender = documentDetail.select(".d-flex.align-items-baseline.label.mb-3:has(.text-secondary.fa.fa-venus-mars.mr-2) .font-weight-bolder").first();
                    crawlJob.setGender(gender != null ? gender.text() : null);

                    Element age = documentDetail.select(".summary-label:contains(Tuổi) + .font-weight-bolder").first();
                    crawlJob.setAge(age != null ? age.text() : null);

                    Element career = documentDetail.select(".d-flex.align-items-baseline.label.mb-3:has(.fa-th) .font-weight-bolder").first();
                    crawlJob.setCareer(career != null ? career.text() : null);

                    // Lấy phần tử tên liên hệ
                    Element contactName = documentDetail.select(".list-unstyled.contact-person li:has(.cli-contact-with) .person-name").first();
                    crawlJob.setContractName(contactName != null ? contactName.text() : null);

                    // Lấy phần tử số điện thoại
                    Element contactPhone = documentDetail.select(".list-unstyled.contact-person li:has(.cli-phone) span").first();
                    crawlJob.setContractPhone(contactPhone != null ? contactPhone.text() : null);

                    // Lấy phần tử email
                    Element contractEmail = documentDetail.select(".list-unstyled.contact-person li:has(.cli-mail) span").first();
                    crawlJob.setContractEmail(contractEmail != null ? contractEmail.text() : null);


                    // Lấy phần tử địa chỉ dựa trên class "cli-location"
                    Element contactAddress = documentDetail.select(".list-unstyled.contact-person li:has(.cli-location) span.align-seft-center").first();
                    crawlJob.setContractAddress(contactAddress != null ? contactAddress.text() : null);


                    // Lấy phần tử ghi chú
                    Element contactNote = documentDetail.select(".list-unstyled.contact-person li:has(.cli-note) .rich-text-content").first();
                    crawlJob.setContractNote(contactNote != null ? contactNote.text() : null);

                    crawlJobRepository.save(crawlJob);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            page++;
        }
    }

    @Override
    public ResponseEntity<?> getData(Pageable pageable) {
        Page<CrawlJob> jobs=crawlJobRepository.findAll(pageable);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
}
