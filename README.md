rate - REST Automatic Test Engine
=================================

People
------
**rate** has been written by Markus Geiss (mgeiss257@gmail.com).

Usage
-----
    final TestScenario testScenario = TestScenario.create("My test scenario", "some useful description")
            .host("http://localhost:4711")
            .path("api")
            .workingHour(WorkingHour.ONE_HOUR)
            .rampUpDelay(RampUpDelay.FIVE_MINUTES)
            .addRobot(100, NewsFeedRobot.class)
            .build();

    testScenario.execute();


License
-------
Copyright 2012 - 2013 Markus Geiss

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
