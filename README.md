<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width">
<title>cardEngine/README.md at develop Andy-Baba/cardEngine
	GitHub</title>
<meta name="description"
	content="A card engine developed in java for educational (and hopefully research) purposes under GNU license. Contribute by creating an account on GitHub">
<link rel="search" type="application/opensearchdescription+xml"
	href="/opensearch.xml" title="GitHub">
<link rel="fluid-icon" href="https://github.com/fluidicon.png"
	title="GitHub">

<meta property="og:image"
	content="https://avatars2.githubusercontent.com/u/45416439?s=400&v=4" />
<meta property="og:site_name" content="GitHub" />
<meta property="og:type" content="object" />
<meta property="og:title" content="Andy-Baba/cardEngine" />
<meta property="og:url" content="https://github.com/nlohmann/json" />
<meta property="og:description"
	content="A card engine developed in java for educational (and hopefully research) purposes under GNU license. Contribute by creating an account on GitHub" />

<meta name="selected-link" value="repo_source" data-pjax-transient>

<meta name="octolytics-host" content="collector.githubapp.com" />
<meta name="octolytics-app-id" content="github" />
<meta name="octolytics-event-url"
	content="https://collector.githubapp.com/github-external/browser_event" />
<meta name="octolytics-dimension-region_edge" content="iad" />
<meta name="octolytics-dimension-region_render" content="iad" />
<meta name="analytics-location"
	content="/&lt;user-name&gt;/&lt;repo-name&gt;/blob/show"
	data-pjax-transient="true" />

<meta name="hostname" content="github.com">
<meta name="user-login" content="">

<meta name="go-import"
	content="github.com/Andy-Baba/cardEngine git https://github.com/Andy-Baba/cardEngine.git">

<meta name="octolytics-dimension-user_id" content="45416439" />
<meta name="octolytics-dimension-user_login" content="Andy-Baba" />
<meta name="octolytics-dimension-repository_id" content="" />
<meta name="octolytics-dimension-repository_nwo"
	content="Andy-Baba/cardEngine" />
<meta name="octolytics-dimension-repository_public" content="true" />
<meta name="octolytics-dimension-repository_is_fork" content="false" />
<meta name="octolytics-dimension-repository_network_root_id"
	content="45416439" />
<meta name="octolytics-dimension-repository_network_root_nwo"
	content="Andy-Baba/cardEngine" />
<meta
	name="octolytics-dimension-repository_explore_github_marketplace_ci_cta_shown"
	content="false" />


<link rel="canonical"
	href="https://github.com/Andy-Baba/cardEngine/README.md"
	data-pjax-transient>


<meta name="browser-stats-url"
	content="https://api.github.com/_private/browser/stats">

<meta name="browser-errors-url"
	content="https://api.github.com/_private/browser/errors">

<meta name="theme-color" content="#1e2327">
<link rel="stylesheet" href="readMeMDStyle.css" />

</head>

<body class="logged-out env-production page-blob">

	<h1 id="top">The Card Engine Library</h1>
	<h3>About Me</h3>
	<p>My name is Andy, I am a software engineer/solution architect.
		Studied software engineering and worked in Huawei company for almost 8
		years. Currently I am doing free lance projects and also teaching
		object oriented / multi-thread programming. I am very interested in
		learning and teaching new software engineering/development concepts. I
		started coding with QBasic on DOS, from an early age (about 9) on my
		first PC equipped with an Intel 80386 and 2MB of RAM -- MY GOSHHHH
		good old times :).</p>
	<p>
		You can checkout my complete profile on <a
			href="https://ir.linkedin.com/in/andisheh-kanani-108387a9">LinkedIn</a>
		page.
	</p>
	<h3>About this Project</h3>
	<p>To help providing my students with a practical sample of basic
		development concepts I started to develop a simple Card Engine Library
		(CEL) developed in java programming language. This project is being
		developed and maintained for educational (and hopefully research)
		purposes under GNU license (including this read me file). We will try
		to release a JAR file version after each release is ready. Contribute
		by creating an account on GitHub Here is the concepts covered in this
		project so far.</p>
	<p>
		<b><i>Note: </i></b>This README will be updated whenever a new
		java/programming concept is add to the code.
	</p>
	<div class="tableOfContent">
		<h3>Table of Concepts:</h3>
		<ul>
			<li><a href="#ttlTestDrivenDevelopment">Test Driven
					Development</a>
				<ol>
					<li><a href="">Junit5</a></li>
					<li><a href="#ttlTimeOut">Testing by time consumption of
							units</a></li>
					<li><a href="doc/com/andybaba/games/card/Hand.html">Using
							of Interfaces</a></li>
				</ol></li>
			<li><a href="#ttlLogging">Logging</a></li>
		</ul>
	</div>
	<hr>
	<div class="content">
		<h2 id="ttlTestDrivenDevelopment">Test Driven Development</h2>
		<p>This approach is very useful specially when your code
			complexity is low to medium, where you can best divide your project
			to well defined classes and methods to do the jobs for you. So you
			start by writing a test class for each of your classes and test units
			for each of your methods, and then you continue by writing your
			actual code to pass each unit test.</p>
		<p>The main advantage of this methodology is potentially it will
			take you to the target of the project in the shortest time possible
			by forcing you to divide the job at hand to sub targets. The you will
			have to unitize them by coding test units and then developing your
			actual program to pass the tests. After you achieve your basic goals
			you may optimize your code and still run the tests to make sure the
			code is functional after each edit.</p>

		<p>
			I personally found that the approach can be later even improved
			further by first writing <b>interfaces</b> for your major classes. So
			to some it up the flow would be:
		</p>
		<ol>
			<li>Divide the project to classes</li>
			<li>Write interfaces corresponding to each class</li>
			<li>Write test units for each interface</li>
			<li>Start coding your actual classes implementing your
				interfaces to pass all the test units</li>
			<li>If you have more time, you can polish your code with more
				details and/or increase the performance</li>
		</ol>
		<p>
			Also you can use logging in your test classes to track what exactly
			happening while the test is running. It will highly help you while
			your are troubleshooting based on your test units. A great example to
			look at is how the <b>Logger class</b> is used in <a
				class="codeExample"
				href="test/com/andybaba/games/card/HandTest.java">Sample Test
				Class</a>
		</p>
		<h3 id="ttlTimeOut">Testing by time consumption of units</h3>
		<p>It is a good practice to consider test units to monitor the
			time consumption of the heavy methods of your program.</p>
		<p>
			<b>Example:</b>
		</p>
		<code>
			<textarea readonly="readonly" rows="7" cols="80"></textarea>
		</code>
	</div>
	<hr>
	<div id="ttlLogging" class="content">
		<h2>Logging</h2>
		<p>One of the best approaches which can help you keep debugging
			and maintenance of your programs under control is using logging. In
			the basic level its pretty easy and straightforward.</p>
	</div>
	<hr>
	<a class="toTop" href="#top">Go To Top</a>
</body>
</html>

