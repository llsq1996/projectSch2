package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class ProjectBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectBootApplication.class, args);
        System.out.println("                           o8888888o\n" +
                "                           88\" . \"88\n" +
                "                           (| -_- |)\n" +
                "                            O\\ = /O\n" +
                "                        ____/`---'\\____\n" +
                "                      .   ' \\\\| |// `.\n" +
                "                       / \\\\||| : |||// \\\n" +
                "                     / _||||| -:- |||||- \\\n" +
                "                       | | \\\\\\ - /// | |\n" +
                "                     | \\_| ''\\---/'' | |\n" +
                "                      \\ .-\\__ `-` ___/-. /\n" +
                "                   ___`. .' /--.--\\ `. . __\n" +
                "                .\"\" '< `.___\\_<|>_/___.' >'\"\".\n" +
                "               | | : `- \\`.;`\\ _ /`;.`/ - ` : | |\n" +
                "                 \\ \\ `-. \\_ __\\ /__ _/ .-` / /\n" +
                "         ======`-.____`-.___\\_____/___.-`____.-'======\n" +
                "                            `=---='\n" +
                "\n" +
                "         .............................................\n" +
                "                 佛祖保佑       永无BUG\n\n");
    }
}
