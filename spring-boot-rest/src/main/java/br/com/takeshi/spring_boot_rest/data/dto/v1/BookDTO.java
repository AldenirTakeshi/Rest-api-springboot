package br.com.takeshi.spring_boot_rest.data.dto.v1;

import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String author;
    private Double price;

    @JsonProperty("launch_date")
    private String launchDateFormatted;

    public BookDTO() {}

    public BookDTO(Long id, String title, String author, Double price, String launchDateFormatted) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.launchDateFormatted = launchDateFormatted;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getLaunchDateFormatted() { return launchDateFormatted; }
    public void setLaunchDateFormatted(String launchDateFormatted) { this.launchDateFormatted = launchDateFormatted; }
}