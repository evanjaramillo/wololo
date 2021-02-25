////////////////////////////////////////////////////////////////////////////////
//    Copyright 2021 Evan R. Jaramillo
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
////////////////////////////////////////////////////////////////////////////////

package com.ejar.wololo;

import com.ejar.wololo.interfaces.IPrivateResponder;
import net.dv8tion.jda.api.entities.User;

public class AbuseResponder implements IPrivateResponder {

    private final User abusiveUser;

    public AbuseResponder(User abusiveUser) {

        this.abusiveUser = abusiveUser;

    }

    @Override
    public void respond() {

        abusiveUser.openPrivateChannel().queue(channel -> {

            channel.sendMessage("Hello " + abusiveUser.getName() +
                    ". We have detected that you are abusing your Wololo bot privileges. " +
                    "_Please don't..._").queue();

        });

    }

}
