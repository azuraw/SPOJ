var nodeio  = require('node.io');
var options = {
    benchmark: false,
    timeout: 999,    //Timeout after 10 seconds
    max: 1,         //Run xx threads concurrently (when run() is async)
    retries: 5,      //Threads can retry x times before failing
    auto_retry: false
};
exports.job = new nodeio.Job(options, {
    init: function() { },
    input: function(start, num, callback) {
        if(start !== 0) return false; // We only want the input method to run once
        var self = this;

        this.getHtml('http://gurmeet.net/puzzles/', function(err, $) {
            if (err) self.exit(err);

            $('div.puzzletext').each(function(elem) {
                var rePattern = new RegExp(/<a href="([^"]+)".*>Solution<\/a>/);
                var arrMatches = elem.innerHTML.match(rePattern);
                callback([arrMatches[1]]);   // array to pass to run()
            });

            callback(null, false);
        });
    },
    run: function(puzzleURL) {
        var self = this;
        console.log("Processing url: " + puzzleURL + "...");
        this.getHtml(puzzleURL, function (err, $) {
            if (err) {
                console.log("ERROR:", err, "PuzzleURL:", puzzleURL);
                self.retry();
            }
            else {
                var text = $('div.puzzle-text').innerHTML;

                // Malformed HTML Hack (for certain particular puzzle)
                if (puzzleURL.indexOf("four-points-two-distinct-distances") !== -1)
                {
                    var soln = $('div.puzzle-source').last().innerHTML;
                }
                else
                {
                    var soln = $('div.puzzle-solution').innerHTML;
                }

                self.emit(text + soln);
            }
        });
    },
    reduce: function(lines) {
        this.emit(lines);
    },
    output: 'solution.html',
    fail: function(input, status) {
        console.log(input+' failed with status: '+status);
        this.emit('failed');
    },
    complete: function(callback) {
        console.log('Job complete.');
        callback();
    }
});
