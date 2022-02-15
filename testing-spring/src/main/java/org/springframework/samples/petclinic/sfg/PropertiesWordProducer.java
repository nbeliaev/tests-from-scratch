package org.springframework.samples.petclinic.sfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("external")
@Primary
public class PropertiesWordProducer implements WordProducer {

    @Value("${sya.word}")
    private String word;

    @Override
    public String getWord() {
        return word;
    }
}
