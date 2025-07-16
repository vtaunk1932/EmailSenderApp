package com.emailsender.emailsenderapp.helper;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Messages {
    private String from;
    private String content;
    private List<String> files;
    private String subjects;
}