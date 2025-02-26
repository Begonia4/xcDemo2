package com.cloudrun.microservicetemplate;

import java.util.ArrayList;

public record Athletes(
    String name,
    ArrayList<sqlData> sqlData
) {}
