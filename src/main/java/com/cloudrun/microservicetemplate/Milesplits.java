package com.cloudrun.microservicetemplate;


public record Milesplits(
    Meta _meta,
    Links _links,
    Embedded _embedded,
    Data[] data)

{
    public record Meta(
        Integer created,
        Cache cache,
        Integer status_code
    )
    {
        public record Cache(
            Boolean fresh,
            Integer ttl
        ) {}
    }

    public record Links(
        Self self
    )
    {
        public record Self(
            String href,
            String method
        ) {}
    }

    public record Embedded(
        Meet meet
    )
    {
        public record Meet(
            String id,
            String profileUrl
        ) {}
    }

    public record Data(
        String id,
        String gender,
        String round,
        String heat,
        String place,
        String units,
        String teamName,
        String firstName,
        String lastName,
        String gradYear,
        String eventName,
        String eventDistance,
        String meetName,
        String divisionName,
        String roundName,
        String genderName,
        String mark) {} 
}

    
    

