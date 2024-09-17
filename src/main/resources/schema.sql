create table if not exists sponsor (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name varchar(255),
  industry varchar(255)
);

create table if not exists event (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    date varchar(255)
);

create table if not exists event_sponsor (
    eventid INT,
    sponsorid INT,
    PRIMARY KEY (eventid, sponsorid),
    FOREIGN KEY(eventid) REFERENCES event(id),
    FOREIGN KEY(sponsorid) REFERENCES sponsor(id)

);