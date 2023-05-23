package com.jmymble;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
public class DemoModel {
    @NonNull
    private String id;

    public static void main(String[] args) {
        DemoModel demoModel = DemoModel.builder().id("abcd").build();
        System.out.println(demoModel);
    }
}
