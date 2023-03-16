package com.x14n.web.controller.monitor;


import com.x14n.common.core.domain.AjaxResult;
import com.x14n.framework.web.domain.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    @GetMapping()
    public AjaxResult getInfo() throws Exception{
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}
